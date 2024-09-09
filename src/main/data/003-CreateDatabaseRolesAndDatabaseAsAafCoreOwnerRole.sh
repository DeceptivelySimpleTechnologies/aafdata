#!/bin/bash

# NOTE: This script creates the other custom roles and the AafCore database in the Postgres database server to be used by Deceptively Simple Technologies Inc's Adaptív Application Foundation (AAF) Data Layer (AAF Data)
# NOTE: Make this script executable with chmod +x 003-CreateDatabaseRolesAndDatabseAsAafCoreOwnerRole.sh
# Usage: ./003-CreateDatabaseRolesAndDatabseAsAafCoreOwnerRole.sh PostgreSQL 14 localhost 5432 AafCoreOwner postgres 0wn3rCl13nt!

customRoleNameOwner="AafCoreOwner"

customRoleNameModeler="AafCoreModeler"
customRoleNamePublisher="AafCorePublisher"
customRoleNameAdministrator="AafCoreAdministrator"
customRoleNameReadWrite="AafCoreReadWrite"
customRoleNameReadOnly="AafCoreReadOnly"

postgresServerGroupName=$1
postgresVersion=$2
postgresHostNameOrIp=$3
postgresPort=$4
postgresUser=$5
postgresDatabase=$6
postgresPassword=$7

export PGPASSWORD=$postgresPassword

echo "Creating custom $customRoleNameModeler role in $postgresServerGroupName $postgresVersion as $customRoleNameOwner role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/001-role/002-AafCoreModeler.sql

echo "Creating custom $customRoleNamePublisher role in $postgresServerGroupName $postgresVersion as $customRoleNameOwner role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/001-role/003-AafCorePublisher.sql

echo "Creating custom $customRoleNameAdministrator role in $postgresServerGroupName $postgresVersion as $customRoleNameOwner role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/001-role/004-AafCoreAdministrator.sql

echo "Creating custom $customRoleNameReadWrite role in $postgresServerGroupName $postgresVersion as $customRoleNameOwner role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/001-role/005-AafCoreReadWrite.sql

echo "Creating custom $customRoleNameReadOnly role in $postgresServerGroupName $postgresVersion as $customRoleNameOwner role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/001-role/006-AafCoreReadOnly.sql

echo "Creating AafCore database in $postgresServerGroupName $postgresVersion as $customRoleNameOwner role"
createdb -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -E UTF8 "AafCore"

echo "Granting CREATE permission on custom $customRoleNamePublisher role in $postgresServerGroupName $postgresVersion as $customRoleNameOwner role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/001-role/007-GrantCreateOnAafCorePublisher.sql

unset PGPASSWORD

# Log in using custom AafCoreOwner role
# TODO: Create custom AafCoreModeler, AafCorePublisher, AafCoreAdministrator, AafCoreReadWrite, AafCoreReadOnly roles and passwords (use AWS SecretManager in the future) and AafCore database
