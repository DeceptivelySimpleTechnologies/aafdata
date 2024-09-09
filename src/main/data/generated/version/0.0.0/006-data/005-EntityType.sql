-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.

-- Table: EntityType.EntityType

INSERT INTO "EntityType"."EntityType"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LocalizedName", "LocalizedDescription", "LocalizedAbbreviation", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, 'bd794423-8dd1-4c96-bc87-4d1948df9bce', -1, 'entitytype-unknown', 'Unknown', 'Represents an as yet undetermined EntityType value, often used as an initial default value or as a temporary placeholder that will be updated in the future', 'Et Ukwn', '', -1, true, '00000000-0000-0000-0000-000000000000', '401380a317f1571d2ce1f4fcfd49d7d84db32aa5db520480112e52cee23bdb18', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, '9d15b06d-f38a-4e33-ae34-8d76a2466503', 0, 'entitytype-none', 'None', 'Represents a value indicating that EntityType does not apply in this case, is intentionally omitted or undefined, or is not relevant', 'Et None', '', -1, true, '00000000-0000-0000-0000-000000000000', '1e875f4550c13a8b546516aa6f270f60ead26fe53e207dbf43a609e2675d6b68', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (1, '6b1bbd72-bcd9-4617-8737-aa135a5d0eb7', 1, 'entitytype-entity-entitytypedefinition', 'EntityTypeDefinition', 'Defines one of the unique business entity types managed by the system', 'Et Etd', '', -1, true, '00000000-0000-0000-0000-000000000000', '7b2838ca4b758251e1a677cca5a15e1ac511452c12f58c9fa4ec150f3df4d5da', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (2, 'a087e8a6-6e02-4049-b8fa-5e0c6bda2062', 1, 'entitytype-entity-entitytypeattribute', 'EntityTypeAttribute', 'Defines one of the attributes that make up each of the unique business entity types managed by the system', 'Et Eta', '', -1, true, '00000000-0000-0000-0000-000000000000', '4397e4afe1df875292f12ad467f500f313591470feaa5c7297ecfd5decbfa0b7', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (3, '22d49b86-c003-422e-8f25-dab0316514c1', 2, 'entitytype-association-entitytypedefinitionentitytypeattributeassociation', 'EntityTypeDefinitionEntityTypeAttributeAssociation', 'Associates one or more EntityTypeAttributes with each EntityTypeDefinition', 'Et EtdEta Assc', '', -1, true, '00000000-0000-0000-0000-000000000000', '799a878232f6dbf3aafacf2187f3b0fac653e3d44b24f4ff1a77a9bfca7a4edc', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (4, 'b445622a-266f-457f-8a42-aeabc0329bc0', 1, 'entitytype-entity-entitysubtype', 'EntitySubtype', 'Defines one of the discrete (at least one per entity instance) values that help to differentiate each of the unique business entity type instances managed by the system, e.g. a GeographicUnit instance may represent a country, state, province, etc, and this distinction is indicated by the instance''s EntitySubtype value', 'Et Est', '', -1, true, '00000000-0000-0000-0000-000000000000', '6e456447e46983ac78658faed696544dfbd1022ca409f83fe714ef6d1c5a4828', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (5, '48e2cda6-fdb2-462f-9798-2f8744045d27', 1, 'entitytype-entity-entitytype', 'EntityType', 'Represents one of the unique business entity type instances managed by the system by persisting its data', 'Et Et', '', -1, true, '00000000-0000-0000-0000-000000000000', '93221704df0cf7bded9d8ccb8219fa3635006e7b6103cd5c77cdbf4720018489', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (7, '7a5f6e2d-7e43-4143-8a34-01e8c02e984f', 1, 'entitytype-entity-geographicunit', 'GeographicUnit', 'A naturally-occurring or artificially-defined area on the surface of the Earth, used to represent territorial, legal, and/or administrative boundaries, e.g. countries, states or provinces, counties, etc', 'Geo Unit', '', -1, true, '00000000-0000-0000-0000-000000000000', '751bd3296b5d66046fe0b7cfd837695920b9295297cdc9598d33ca0b32a45709', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (8, '81a7fe2b-1d6a-43b6-9ebb-b10b937bed55', 3, 'entitytype-hierarchy-geographicunithierarchy', 'GeographicUnitHierarchy', 'Defines hierarchical parent/child relationships between GeographicUnits, resulting in tree structures that represent containment or membership, e.g. these states belong to this country, etc', 'Geo Unt Hier', '', -1, true, '00000000-0000-0000-0000-000000000000', 'bc4764a75d58117857baef13e1cd42c06e9e5e636af123620597c03e68d11f0e', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (9, '4593af3a-032c-46ed-9462-50654d7a43b3', 1, 'entitytype-entity-language', 'Language', 'A system of spoken words or sounds, written symbols, and rules used for communication', 'Lang', '', -1, true, '00000000-0000-0000-0000-000000000000', '15e9daaee6b84b30d894cfda43302a58d52d3a4ba806f10ed3818175063e7ef8', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (10, 'bea276a4-c36b-4445-86a9-6b948147e65a', 1, 'entitytype-entity-locale', 'Locale', 'The combination of a Language and a GeographicUnit that represents regional linguistic variations, e.g. U.S. vs U.K. English, and that is necessary in order to meaningfully communicate, including the valid representation of dates, times, currency amounts, etc', 'Locale', '', -1, true, '00000000-0000-0000-0000-000000000000', '656932e024a250420f5ab706f892641a4e9b9e78120d1355f8d8783bc0661ca8', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (14, 'a0f1152b-da0f-43da-b3d2-979a24a96eb7', 1, 'entitytype-entity-person', 'Person', 'An individual that has legal standing according to the law and that has the legal ability to enter into formal and informal contracts, assume business obligations, incur and pay debts, sue and be sued in its own right and be held responsible for its individual actions. Note: Corporations, government agencies, etc are all Organizations at their "core" while vendors, customers, etc can be either Organizations or individual Persons.  Persons employed by Organizations can be represented as Employees, and Persons or Employees who are authorized to access and interact with InformationSystems are represented as InformationSystemUsers', 'Person', '', -1, true, '00000000-0000-0000-0000-000000000000', '2473e8cd73697c075e064c74817d5c9dd9b01b9c70e8c8da57e01defd3f49a2f', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (15, '30abc73d-7c18-4480-a5e8-bc68d907960d', 1, 'entitytype-entity-organization', 'Organization', 'A corporate legal entity e.g. proprietorship, partnership, or corporation made up of a group of individual Persons and/or other Organizations that has legal standing according to the law and that has the legal ability to enter into formal and informal contracts, assume business obligations, incur and pay debts, sue and be sued in its own right, and be held responsible for its corporate actions.  Note: Corporations, government agencies, etc are all Organizations at their "core" while vendors, customers, etc can be either Organizations or individual Persons.  Organizations are often internally structured using OrganizationalUnits e.g. department, division, region, etc', 'Org', '', -1, true, '00000000-0000-0000-0000-000000000000', '976d1ba03bb15485f34a461d83828b0d4db6f9e8280aabe52acd5e1ca95b5ab0', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (16, '7b365cf8-efbf-40ba-a47d-665c6361fbe1', 1, 'entitytype-entity-organizationalunit', 'OrganizationalUnit', 'An instance of an Organization''s internal organizational structure e.g. a department, a division, a region, etc.  OrganizationalUnits can reflect a functional structure, e.g. Marketing Department, Finance Department, Sales Department, etc, a geographic structure, e.g. Eastern Division, Western Division, etc, or any other organizational structure', 'Org Unit', '', -1, true, '00000000-0000-0000-0000-000000000000', '5b9eaee9fdada84b4930898d2cfb88b50cebbe8a103dfb39e58c14edc1a895d5', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (17, 'd60c421e-b8c3-4b59-918c-15f0be7a1277', 3, 'entitytype-hierarchy-organizationalunithierarchy', 'OrganizationalUnitHierarchy', 'An OrganizationalUnit''s location in an Organization''s internal organizational structure e.g. one of several teams within a department, a region within a division, etc.  OrganizationalUnitHierarchys enable us to re-organize OrganizationalUnits by changing their relative locations in the hierarchy without changing the internal state of the OrganizationalUnits themselves', 'Org Unit Hier', '', -1, true, '00000000-0000-0000-0000-000000000000', 'aa9e720d0f43b5409e1d5e4e8f24dbf27b1a2222b8daa64d98ef67a06a2650c2', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (18, 'a79bc3c2-ef1e-4d91-9275-f784f9a757ed', 1, 'entitytype-entity-employee', 'Employee', 'A Person who is employed to work for an Organization.  A Person may be employed by one or more Organizations, which will result in the Person having one or more Employee Ids.  An Employee may be assigned to zero or more of his/her Organization''s OrganizationalUnits and is assigned to one or more EmployeeRoles', 'Empl', '', -1, true, '00000000-0000-0000-0000-000000000000', 'a9e448edcb092a87672fec35da7e4958c74f1617f73876324e49fb1928901e6e', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (19, '76ffe754-244b-447f-a192-d5ed11222c70', 1, 'entitytype-entity-legalentity', 'LegalEntity', 'Either an individual Person or a corporate legal entity i.e. an Organization that has legal standing according to the law and has the legal ability to enter into formal and informal contracts, assume business obligations, incur and pay debts, sue and be sued in its own right, and be held responsible for its actions.  Note: Abstracting Person, Employee, or Organization as a LegalEntity simplifies the code that would otherwise be needed to manage these different entity types', 'Legal Ent', '', -1, true, '00000000-0000-0000-0000-000000000000', 'caf38ce7fb03328aa20b192cacfad9e63435ee67cdfab195431d7c07d33271e2', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;