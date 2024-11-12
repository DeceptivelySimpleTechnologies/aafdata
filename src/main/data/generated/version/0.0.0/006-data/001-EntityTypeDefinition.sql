-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: EntityTypeDefinition.EntityTypeDefinition

INSERT INTO "EntityTypeDefinition"."EntityTypeDefinition"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LocalizedName", "LocalizedDescription", "LocalizedAbbreviation", "VersionTag", "DataLocationEntitySubtypeId", "DataStructureEntitySubtypeId", "PublishedAtDateTimeUtc", "PublishedByInformationSystemUserId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (1, '07993739-be47-41f2-96b6-902dbf33615a', 1, 'entitytypedefinition-entitytypedefinition', 'EntityTypeDefinition', 'Defines one of the unique business entity types managed by the system', 'Ent Typ Def', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 1, true, '00000000-0000-0000-0000-000000000000', 'bb51fd4cf5b9afbeb4e0ad16c71cf05196173d83df313300fc28edeecf3eedb0', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (2, '685b051c-db9b-47a4-acfd-ea964e3c7ada', 1, 'entitytypedefinition-entitytypeattribute', 'EntityTypeAttribute', 'Defines one of the attributes that make up each of the unique business entity types managed by the system', 'Ent Typ Atr', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 2, true, '00000000-0000-0000-0000-000000000000', '96641a063287f40c4cb186c8494e5f509d70435d2969a60651af87bfac611b26', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (3, '5a6f4b20-e48a-4894-8045-192c221e64e2', 1, 'entitytypedefinition-entitytypedefinitionentitytypeattributeassociation', 'EntityTypeDefinitionEntityTypeAttributeAssociation', 'Associates one or more EntityTypeAttributes with each EntityTypeDefinition', 'Ent Typ Atr Asc', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 3, true, '00000000-0000-0000-0000-000000000000', 'f48111f10205876f4b5ccfc7425fcc246e899b3db3f0f6e18bd404ce599ccb88', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (4, 'e043deaa-f0a1-497d-9c7a-9ffbb665cc91', 1, 'entitytypedefinition-entitysubtype', 'EntitySubtype', 'Defines one of the discrete (at least one per entity instance) values that help to describe and further differentiate each of the unique business entity type instances managed by the system', 'Ent Sub', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 4, true, '00000000-0000-0000-0000-000000000000', '101c5592b4b36b9c7025982304081e0e84d86a9ffa779710c5665f60fbbb4057', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (5, '76c0ae06-6dd7-41e0-bb93-e407768e4a6c', 1, 'entitytypedefinition-entitytype', 'EntityType', 'Represents one of the unique business entity type instances managed by the system by persisting its data', 'Ent Typ', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 5, true, '00000000-0000-0000-0000-000000000000', '6a08c8ddec096de76283da71415ebc0054f7116cf2049c3a8e7a60cd904edb8b', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (7, 'f4b2d8f4-1793-4bea-ab60-b69e5d6350e1', 1, 'entitytypedefinition-geographicunit', 'GeographicUnit', 'A naturally-occurring or artificially-defined area on the surface of the Earth, used to represent territorial, legal, and/or administrative boundaries, e.g. countries, states or provinces, counties, etc', 'Geo Unt', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 7, true, '00000000-0000-0000-0000-000000000000', '65d047640589172ffdeca6ca75bcf4b90f273cce985ee65f973c0d6c57489fb7', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (8, '1ac25e84-ae3a-4cf5-9945-a1e685da256c', 1, 'entitytypedefinition-geographicunithierarchy', 'GeographicUnitHierarchy', 'Defines hierarchical parent/child relationships between GeographicUnits, resulting in tree structures that represent containment or membership, e.g. these states belong to this country, etc', 'Geo Unt Hir', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 8, true, '00000000-0000-0000-0000-000000000000', '42e49df12ded1ad98360a61de3eabea47c38039ab3da75369156d35816aa050d', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (9, '128672e8-ff12-49e3-aad1-5a11ddec0ff3', 1, 'entitytypedefinition-language', 'Language', 'A system of spoken words or sounds, written symbols, and rules used for communication', 'Lang', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 9, true, '00000000-0000-0000-0000-000000000000', '8334b94d4761aa912117cec5dcaf2d061b91f59c4f1de3f60e25f18d7d10d182', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (10, '66124db4-f1e1-46b3-a001-478db235d0b6', 1, 'entitytypedefinition-locale', 'Locale', 'The combination of a Language and a GeographicUnit that represents regional linguistic variations, e.g. U.S. vs U.K. English, and that is necessary in order to meaningfully communicate, including the valid representation of dates, times, currency amounts, etc', 'Loc', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 10, true, '00000000-0000-0000-0000-000000000000', '201cb8d3454eb280b76caa419aedebcb96c6a02ab5325a7f9de94fc7e860b7bb', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (14, '40af5bd5-e99a-4fc1-a5e7-11c611f6217e', 1, 'entitytypedefinition-person', 'Person', 'An individual that has legal standing according to the law and that has the legal ability to enter into formal and informal contracts, assume business obligations, incur and pay debts, sue and be sued in its own right and be held responsible for its individual actions. Note: Corporations, government agencies, etc are all Organizations at their "core" while vendors, customers, etc can be either Organizations or individual Persons.  Persons employed by Organizations can be represented as Employees, and Persons or Employees who are authorized to access and interact with InformationSystems are represented as InformationSystemUsers', 'Prs', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 14, true, '00000000-0000-0000-0000-000000000000', 'dd417b0625f828106b033870991e25f5343b3e73626e5c0eec2252729ec85038', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (15, '736fe979-2988-4fbd-b5fa-22acec1ebd77', 1, 'entitytypedefinition-organization', 'Organization', 'A corporate legal entity e.g. proprietorship, partnership, or corporation made up of a group of individual Persons and/or other Organizations that has legal standing according to the law and that has the legal ability to enter into formal and informal contracts, assume business obligations, incur and pay debts, sue and be sued in its own right, and be held responsible for its corporate actions.  Note: Corporations, government agencies, etc are all Organizations at their "core" while vendors, customers, etc can be either Organizations or individual Persons.  Organizations are often internally structured using OrganizationalUnits e.g. department, division, region, etc', 'Org', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 15, true, '00000000-0000-0000-0000-000000000000', '31a0897dee9e9d532785dfcd63692b14b33f861374db812ad54145417642ca4c', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (16, '7cfd3799-8f97-4d82-9cfc-40a1aff9c7fe', 1, 'entitytypedefinition-organizationalunit', 'OrganizationalUnit', 'An instance of an Organization''s internal organizational structure e.g. a department, a division, a region, etc.  OrganizationalUnits can reflect a functional structure, e.g. Marketing Department, Finance Department, Sales Department, etc, a geographic structure, e.g. Eastern Division, Western Division, etc, or any other organizational structure', 'Org Unt', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 16, true, '00000000-0000-0000-0000-000000000000', '5691240c2c006d5bdaedd168cc7799cda3c57cd19e215e7b20aa0a16ef66cc56', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (17, '9b9064a5-cc24-4486-99dd-7d4b47ad0810', 1, 'entitytypedefinition-organizationalunithierarchy', 'OrganizationalUnitHierarchy', 'An OrganizationalUnit''s location in an Organization''s internal organizational structure e.g. one of several teams within a department, a region within a division, etc.  OrganizationalUnitHierarchys enable us to re-organize OrganizationalUnits by changing their relative locations in the hierarchy without changing the internal state of the OrganizationalUnits themselves', 'Org Unt Hir', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 17, true, '00000000-0000-0000-0000-000000000000', '4845bcde960335b1020d7c9c8bf32a49628e5b1dc9e7f181ad712ca5d3924af4', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (18, '9b123101-83ab-487c-86de-adca3c3b594c', 1, 'entitytypedefinition-employee', 'Employee', 'A Person who is employed to work for an Organization.  A Person may be employed by one or more Organizations, which will result in the Person having one or more Employee Ids.  An Employee may be assigned to zero or more of his/her Organization''s OrganizationalUnits and is assigned to one or more EmployeeRoles', 'Emp', '000.000.000', 4, 1, '2022-06-01 00:00:00.000', 0, '', 18, true, '00000000-0000-0000-0000-000000000000', 'e93a190df5e1b4efc0b6df7efe93df41ec7766049440db5917cea07c8f3d4b33', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (19, 'c2989912-5943-4a77-a3b9-3c5ccacd974d', 1, 'entitytypedefinition-legalentity', 'LegalEntity', 'Either an individual Person or a corporate legal entity i.e. an Organization that has legal standing according to the law and has the legal ability to enter into formal and informal contracts, assume business obligations, incur and pay debts, sue and be sued in its own right, and be held responsible for its actions.  Note: Abstracting Person, Employee, or Organization as a LegalEntity simplifies the code that would otherwise be needed to manage these different entity types', 'Leg Ent', '000.000.000', 4, 1, '9999-12-31 23:59:59.999', 0, '', 19, true, '00000000-0000-0000-0000-000000000000', '08dcaa7ac4769827291da41733c8bdf0b078bb8b3d915a68aa773eef70023546', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;

--     (1, uuid_generate_v4(), 1, 'entitytypedefinition-entitytypedefinition', 'EntityTypeDefinition', 'Defines one of the unique business entity types managed by the system', 'Etd Etd', "000.000.000", 4, 6, '', -1, true, '00000000-0000-0000-0000-000000000000', '8d9c19e7c15f80be666ad3fed9c5334228724245', '2022-06-01T00:00:00.000Z', 0, '2022-06-01T00:00:00.000Z', 0, '9999-12-31T23:59.999Z', 0),
-- /Library/PostgreSQL/14/bin/pg_dump --data-only --column-inserts --username=postgres AafCore > aaf-core-data.sql