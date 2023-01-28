-- Table: EntityTypeDefinition.EntityTypeDefinition

INSERT INTO "EntityTypeDefinition"."EntityTypeDefinition"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LocalizedName", "LocalizedDescription", "VersionTag", "LocalizedAbbreviation", "DataLocationEntitySubtypeId", "DataStructureEntitySubtypeId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (1, '07993739-be47-41f2-96b6-902dbf33615a', 1, 'entitytypedefinition-entitytypedefinition', 'EntityTypeDefinition', 'Defines one of the unique business entity types managed by the system', 'Etd Etd', "000.000.000", 4, 1, '', -1, true, '00000000-0000-0000-0000-000000000000', '8d9c19e7c15f80be666ad3fed9c5334228724245', '2022-06-01T00:00:00.000Z', 0, '2022-06-01T00:00:00.000Z', 0, '9999-12-31T23:59.999Z', 0),
    (2, '685b051c-db9b-47a4-acfd-ea964e3c7ada', 1, 'entitytypedefinition-entitytypeattribute', 'EntityTypeAttribute', 'Defines one of the attributes that make up each of the unique business entity types managed by the system', 'Etd Eta', "000.000.000", 4, 1, '', -1, true, '00000000-0000-0000-0000-000000000000', '3dd8c73929c9c5747ba5328cd5300fbd40243c67', '2022-06-01T00:00:00.000Z', 0, '2022-06-01T00:00:00.000Z', 0, '9999-12-31T23:59.999Z', 0),
    (3, '5a6f4b20-e48a-4894-8045-192c221e64e2', 1, 'entitytypedefinition-entitytypedefinitionentitytypeattributeassociation', 'EntityTypeDefinitionEntityTypeAttributeAssociation', 'Associates one or more EntityTypeAttributes with each EntityTypeDefinition', 'Etd Eta Assc', "000.000.000", 4, 1, '', -1, true, '00000000-0000-0000-0000-000000000000', 'cdf486236ef1bac96ac7460a1cef14b8ccf4dba3', '2022-06-01T00:00:00.000Z', 0, '2022-06-01T00:00:00.000Z', 0, '9999-12-31T23:59.999Z', 0),
    (4, 'e043deaa-f0a1-497d-9c7a-9ffbb665cc91', 1, 'entitytypedefinition-entitysubtype', 'EntitySubtype', 'Defines one of the discrete (at least one per entity instance) values that help to describe and further differentiate each of the unique business entity type instances managed by the system', 'Etd Est', "000.000.000", 4, 1, '', -1, true, '00000000-0000-0000-0000-000000000000', '45da6c1455e4281937a257f6f8ab070b7b4dc203', '2022-06-01T00:00:00.000Z', 0, '2022-06-01T00:00:00.000Z', 0, '9999-12-31T23:59.999Z', 0),
    (5, '76c0ae06-6dd7-41e0-bb93-e407768e4a6c', 1, 'entitytypedefinition-entitytype', 'EntityType', 'Represents one of the unique business entity type instances managed by the system by persisting its data', 'Etd Et', "000.000.000", 4, 1, '', -1, true, '00000000-0000-0000-0000-000000000000', '2129f496bbdf8cc21cf143e4834be4920113e435', '2022-06-01T00:00:00.000Z', 0, '2022-06-01T00:00:00.000Z', 0, '9999-12-31T23:59.999Z', 0)
;

--     (1, uuid_generate_v4(), 1, 'entitytypedefinition-entitytypedefinition', 'EntityTypeDefinition', 'Defines one of the unique business entity types managed by the system', 'Etd Etd', "000.000.000", 4, 1, '', -1, true, '00000000-0000-0000-0000-000000000000', '8d9c19e7c15f80be666ad3fed9c5334228724245', '2022-06-01T00:00:00.000Z', 0, '2022-06-01T00:00:00.000Z', 0, '9999-12-31T23:59.999Z', 0),
-- /Library/PostgreSQL/14/bin/pg_dump --data-only --column-inserts --username=postgres AafCore > aaf-core-data.sql