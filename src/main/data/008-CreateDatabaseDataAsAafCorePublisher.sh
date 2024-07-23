#!/bin/bash

# NOTE: This script adds lookup/validation data to the AafCore database tables in the Postgres database server to be used by Deceptively Simple Technologies Inc's Adaptív Application Foundation (AAF) Data Layer (AAF Data)
# NOTE: Make this script executable with chmod +x 008-CreateDatabaseDataAsAafCorePublisher.sh
# Usage: ./008-CreateDatabaseDataAsAafCorePublisher.sh PostgreSQL 14 localhost 5432 AafCorePublisher AafCore Publ15h3rCl13nt!

postgresServerGroupName=$1
postgresVersion=$2
postgresHostNameOrIp=$3
postgresPort=$4
postgresUser=$5
postgresDatabase=$6
postgresPassword=$7

export PGPASSWORD=$postgresPassword

echo "Adding EntityTypeDefinition data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/001-EntityTypeDefinition.sql

echo "Adding EntityTypeAttribute data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/002-EntityTypeAttribute.sql

echo "Adding EntityTypeDefinitionEntityTypeAttributeAssociation data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/003-EntityTypeDefinitionEntityTypeAttributeAssociation.sql

echo "Adding EntitySubtype data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/004-EntitySubtype.sql

echo "Adding EntityType data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/005-EntityType.sql

unset PGPASSWORD

# Log in using custom AafCorePublisher role
# Create AafCore database table lookup/validation data
