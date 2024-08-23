#!/bin/bash

# NOTE: This script adds custom functions to the AafCore database in the Postgres database server to be used by Deceptively Simple Technologies Inc's Adaptív Application Foundation (AAF) Data Layer (AAF Data)
# NOTE: Make this script executable with chmod +x 007-CreateDatabaseFunctionsAsAafCorePublisher.sh
# Usage: ./007-CreateDatabaseFunctionsAsAafCorePublisher.sh PostgreSQL 14 localhost 5432 AafCorePublisher AafCore Publ15h3rCl13nt!

postgresServerGroupName=$1
postgresVersion=$2
postgresHostNameOrIp=$3
postgresPort=$4
postgresUser=$5
postgresDatabase=$6
postgresPassword=$7

export PGPASSWORD=$postgresPassword

echo "Adding GetMinimumDateTimeUtc function to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/005-function/001-GetMinimumDateTimeUtc.sql

echo "Adding GetMaximumDateTimeUtc function to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/005-function/002-GetMaximumDateTimeUtc.sql

echo "Adding GetEntityTypeDefinitions function to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/005-function/003-GetEntityTypeDefinitions.sql

echo "Adding GetEntityTypeAttributes function to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/005-function/004-GetEntityTypeAttributes.sql

echo "Adding GetEntityTypeDefinitionEntityTypeAttributeAssociations function to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/005-function/005-GetEntityTypeDefinitionEntityTypeAttributeAssociations.sql

echo "Adding GetEntitySubtypes function to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/005-function/006-GetEntitySubtypes.sql

echo "Adding EntityDataCreate function to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/005-function/007-EntityDataCreate.sql

echo "Adding EntityDataRead function to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/005-function/008-EntityDataRead.sql

unset PGPASSWORD

# Log in using custom AafCorePublisher role
# Create AafCore database functions
