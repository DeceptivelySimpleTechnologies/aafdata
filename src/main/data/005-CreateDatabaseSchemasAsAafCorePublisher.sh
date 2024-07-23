#!/bin/bash

# NOTE: This script adds custom entity schemas to the AafCore database in the Postgres database server to be used by Deceptively Simple Technologies Inc's Adaptív Application Foundation (AAF) Data Layer (AAF Data)
# NOTE: Make this script executable with chmod +x 005-CreateDatabaseSchemasAsAafCorePublisher.sh
# Usage: ./005-CreateDatabaseSchemasAsAafCorePublisher.sh PostgreSQL 14 localhost 5432 AafCorePublisher AafCore Publ15h3rCl13nt!

postgresServerGroupName=$1
postgresVersion=$2
postgresHostNameOrIp=$3
postgresPort=$4
postgresUser=$5
postgresDatabase=$6
postgresPassword=$7

export PGPASSWORD=$postgresPassword

echo "Adding EntityTypeDefinition schema to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/003-schema/001-EntityTypeDefinition.sql

echo "Adding EntityTypeAttribute schema to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/003-schema/002-EntityTypeAttribute.sql

echo "Adding EntityTypeDefinitionEntityTypeAttributeAssociation schema to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/003-schema/003-EntityTypeDefinitionEntityTypeAttributeAssociation.sql

echo "Adding EntitySubtype schema to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/003-schema/004-EntitySubtype.sql

echo "Adding EntityType schema to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/003-schema/005-EntityType.sql

unset PGPASSWORD

# Log in using custom AafCorePublisher role
# Create AafCore database schemas
