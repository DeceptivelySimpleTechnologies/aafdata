#!/bin/bash

# NOTE: This script creates the custom AafCoreOwner role in the Postgres database server to be used by Deceptively Simple Technologies Inc's Adaptív Application Foundation (AAF) Data Layer (AAF Data)
# NOTE: Make this script executable with chmod +x 003-CreateDatabaseRolesAsAafCoreOwnerRole.sh
# Usage: ./003-CreateDatabaseRolesAsAafCoreOwnerRole.sh PostgreSQL 14

customRoleNameOwner="AafCoreOwner"

postgresServerGroupName=$1
postgresVersion=$2

echo "Creating custom $customRoleNameOwner role in $postgresServerGroupName $postgresVersion as default $postgresDefaultRoleName role"

# Log in using new postgres role password
# Create custom AafCoreOwner role and password, and return password (use AWS SecretManager in the future)
