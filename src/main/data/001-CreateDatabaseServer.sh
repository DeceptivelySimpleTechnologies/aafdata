#!/bin/bash

# NOTE: This script creates the Postgres database server to be used by Deceptively Simple Technologies Inc's Adaptív Application Foundation (AAF) Data Layer (AAF Data)
# NOTE: Make this script executable with chmod +x 001-CreateDatabaseServer.sh
# Usage: ./001-CreateDatabaseServer.sh PostgreSQL 14 localhost 5432 postgres postgres postgres

postgresServerGroupName=$1
postgresVersion=$2
postgresHostNameOrIp=$3
postgresPort=$4
postgresUser=$5
postgresDatabase=$6
postgresPassword=$7

echo "Creating ServerGroup $postgresServerGroupName $postgresVersion"

export PGPASSWORD=$postgresPassword
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f 
unset PGPASSWORD

# Log in using default postgres role password
# TODO: Change default postgres role password and return new password (use AWS SecretManager in the future)
