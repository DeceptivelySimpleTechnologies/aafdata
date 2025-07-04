#!/bin/bash

# NOTE: This script adds lookup/validation data to the AafCore database tables in the Postgres database server to be used by Deceptively Simple Technologies Inc's Adaptív Application Foundation (AAF) Data Layer (AAF Data)
# NOTE: Make this script executable with chmod +x 008-CreateDatabaseDataAsAafCoreModeler.sh
# Usage: ./008-CreateDatabaseDataAsAafCoreModeler.sh PostgreSQL 14 localhost 5432 AafCoreModeler AafCore M0d3l3rCl13nt!

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

echo "Adding EntityType data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/005-EntityType.sql

echo "Adding EntitySubtype data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/004-EntitySubtype.sql

echo "Adding GeographicUnit data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/007-GeographicUnit.sql

echo "Adding GeographicUnitHierarchy data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/008-GeographicUnitHierarchy.sql

echo "Adding Language data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/009-Language.sql

echo "Adding Locale data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/010-Locale.sql

echo "Adding Currency data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/011-Currency.sql

echo "Adding Person data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/014-Person.sql

echo "Adding Organization data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/015-Organization.sql

echo "Adding OrganizationalUnit data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/016-OrganizationalUnit.sql

echo "Adding OrganizationalUnitHierarchy data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/017-OrganizationalUnitHierarchy.sql

echo "Adding Employee data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/018-Employee.sql

echo "Adding LegalEntity data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/019-LegalEntity.sql

echo "Adding InternetDomainLabel data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/020-InternetDomainLabel.sql

echo "Adding InternetDomainLabelHierarchy data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/021-InternetDomainLabelHierarchy.sql

echo "Adding TelephoneNumber data to $postgresDatabase database in $postgresServerGroupName $postgresVersion as $postgresUser role"
psql -h $postgresHostNameOrIp -p $postgresPort -U $postgresUser -d $postgresDatabase -f generated/version/0.0.0/006-data/022-TelephoneNumber.sql

unset PGPASSWORD

# Log in using custom AafCoreModeler role
# Create AafCore database table lookup/validation data
