#!/bin/bash

###########################################################
### Application name configuration
###########################################################
app_name="delete-logistics-mapping-services"

###########################################################
### Server to environment mapping
###########################################################
declare -A server_mapping

server_mapping["devapv0532"]="dev"
server_mapping["devapv0547"]="int"
server_mapping["devapv0548"]="qat"
server_mapping["devapv0549"]="uat"

declare -A ehi_services_auth_targets
ehi_services_auth_targets["dev"]="rsi-auth-iprod"
ehi_services_auth_targets["int"]="rsi-auth-iprod"
ehi_services_auth_targets["qat"]="rsi-auth-extqa"
ehi_services_auth_targets["uat"]="rsi-auth-iprod"
ehi_services_auth_targets["prod"]="rsi-auth-prod"


###########################################################
### General host and environment configuration
###########################################################

# Get host name (without domain)
t_host="$(uname -n | cut -d. -f 1)"

# Look up environment based on host name
t_app_env="${server_mapping[$t_host]}"

if [ -z "$t_app_env" ]; then
   echo "No environment configured for host." >&2
   exit 1
fi

ehi_services_auth_target="${ehi_services_auth_targets[$t_app_env]}"

###########################################################
### Tomcat application classpath customization
###########################################################
# Classpath is the same across all environments
# APP_CLASSPATH="/foo/bar/baz.jar"
APP_CLASSPATH=""
echo "APP_CLASSPATH=${APP_CLASSPATH}"


###########################################################
### Environment-specific JAVA_OPTS
###########################################################
declare -A APP_JAVA_OPTS
APP_JAVA_OPTS="-Dspring.profiles.active=${t_app_env},${ehi_services_auth_target}"
APP_JAVA_OPTS="$APP_JAVA_OPTS -Derac.serverLevel=${t_app_env}"
APP_JAVA_OPTS="$APP_JAVA_OPTS -DprojectName=deleteLogisticsMapping"
APP_JAVA_OPTS="$APP_JAVA_OPTS -DepsPath=/opt/tomcat/scratch/eps.${app_name}.${t_app_env}.properties"

# Tivoli configuration for JAVA_OPTS
if [ "$t_app_env"  = "prod" ]
then
    APP_JAVA_OPTS="$APP_JAVA_OPTS -DTec_Server1=pri-evt-svr:55501"
    APP_JAVA_OPTS="$APP_JAVA_OPTS -DTec_Server2=sec-evt-svr:55501"
else
    APP_JAVA_OPTS="$APP_JAVA_OPTS -DTec_Server1=dev-pri-evt-svr:55501"
    APP_JAVA_OPTS="$APP_JAVA_OPTS -DTec_Server2=dev-sec-evt-svr:55501"
fi

# Configure TLSv1.2 so when we connect to https endpoints that are locked down
# we can establish the connection. By default JDK 7 does not hvae TLSv1.2
# enabled, so we enable it here. Without this, if a server doesn't support any
# protocol below TLSv1.2, eg. TLS1.1 or TLS1.0, we won't be able to connect.
# JDK 8 should have this enabled by default.
# APP_JAVA_OPTS="$APP_JAVA_OPTS -Dhttps.protocols=TLSv1.2"

if [ -z "$APP_JAVA_OPTS" ]; then
    echo "No APP_JAVA_OPTS configured for environment." >&2
    exit 1
fi
echo "APP_JAVA_OPTS=${APP_JAVA_OPTS}"

###########################################################
### Keystore configuration for HTTPS
###########################################################
# Keystore alias name mapping for HTTPS configuration
# Replace <env name> placeholders with the actual environment names, eg. dev, int, qa, etc.
# Replace <keystore alias environment name> with the environment name in the keystore file, eg. dev, int, qa, etc.
# Note that sometimes the environment name here may not match the environment name of the keystore alias, eg. prd vs. prod
# In this case we have a wildcard cert *.vehiclesales so we will just set the KEY_ALIAS to vehiclesales

#echo "KEY_ALIAS=vsc"


###########################################################
### Configure Tomcat to enable connections on HTTP, HTTPS or both
###########################################################
# Configure whether Tomcat listens on HTTP, HTTPS or both
# Valid values are "http", "https" and "both"
CONNECTOR=both
echo "CONNECTOR=${CONNECTOR}"

###########################################################
### Tomcat x-forwarded-for header configuration
###########################################################
# This configures Tomcat to recognize the x-forwarded-for header; this is used when rewriting URLs with Tomcat.
X_FORWARDED_FOR=true
echo "X_FORWARDED_FOR=${X_FORWARDED_FOR}"