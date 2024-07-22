#!/bin/bash

# NOTE: This script creates the custom AafCoreOwner role in the Postgres database server to be used by Deceptively Simple Technologies Inc's Adaptív Application Foundation (AAF) Data Layer (AAF Data)
# NOTE: Make this script executable with chmod +x 002-CreateDatabaseRoleAafCoreOwnerAsPostgres.sh
# Usage: ./002-CreateDatabaseRoleAafCoreOwnerAsPostgresRole.sh PostgreSQL 14 localhost 5432 postgres postgres postgres

customRoleNameOwner="AafCoreOwner"

postgresServerGroupName=$1
postgresVersion=$2
postgresHostNameOrIp=$3
postgresPort=$4
postgresUser=$5
postgresDatabase=$6
postgresPassword=$7

echo "Creating custom $customRoleNameOwner role in $postgresServerGroupName $postgresVersion as $postgresUser role"

export PGPASSWORD=$postgresPassword
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/001-role/001-AafCoreOwner.sql
unset PGPASSWORD

# Log in using new postgres role password
# Create custom AafCoreOwner role and password, and return password (use AWS SecretManager in the future)
