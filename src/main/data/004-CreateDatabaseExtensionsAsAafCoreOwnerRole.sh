#!/bin/bash

# NOTE: This script adds UUID extensions to the AafCore database in the Postgres database server to be used by Deceptively Simple Technologies Inc's Adaptív Application Foundation (AAF) Data Layer (AAF Data)
# NOTE: Make this script executable with chmod +x 004-CreateDatabaseExtensionsAsAafCoreOwnerRole.sh
# Usage: ./004-CreateDatabaseExtensionsAsAafCoreOwnerRole.sh PostgreSQL 14 localhost 5432 AafCoreOwner AafCore 0wn3rCl13nt!

postgresServerGroupName=$1
postgresVersion=$2
postgresHostNameOrIp=$3
postgresPort=$4
postgresUser=$5
postgresDatabase=$6
postgresPassword=$7

export PGPASSWORD=$postgresPassword

echo "Adding UUID extentions to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/002-database/002-Uuid-Extension.sql

unset PGPASSWORD

# Log in using custom AafCoreOwner role
# Create AafCore database and UUID extensions
