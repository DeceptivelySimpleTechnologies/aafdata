#!/bin/bash

# NOTE: This script adds custom entity tables to the AafCore database in the Postgres database server to be used by Deceptively Simple Technologies Inc's Adaptív Application Foundation (AAF) Data Layer (AAF Data)
# NOTE: Make this script executable with chmod +x 006-CreateDatabaseTablesAsAafCorePublisher.sh
# Usage: ./006-CreateDatabaseTablesAsAafCorePublisher.sh PostgreSQL 14 localhost 5432 AafCorePublisher AafCore Publ15h3rCl13nt!

postgresServerGroupName=$1
postgresVersion=$2
postgresHostNameOrIp=$3
postgresPort=$4
postgresUser=$5
postgresDatabase=$6
postgresPassword=$7

export PGPASSWORD=$postgresPassword

echo "Adding EntityTypeDefinition table to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/004-model/001-EntityTypeDefinition.sql

echo "Adding EntityTypeAttribute table to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/004-model/002-EntityTypeAttribute.sql

echo "Adding EntityTypeDefinitionEntityTypeAttributeAssociation table to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/004-model/003-EntityTypeDefinitionEntityTypeAttributeAssociation.sql

echo "Adding EntitySubtype table to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/004-model/004-EntitySubtype.sql

echo "Adding EntityType table to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/004-model/005-EntityType.sql

unset PGPASSWORD

# Log in using custom AafCorePublisher role
# Create AafCore database tables
