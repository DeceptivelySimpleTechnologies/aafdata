-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: ContentItemGroup.ContentItemGroup

INSERT INTO "ContentItemGroup"."ContentItemGroup"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LocalizedName", "LocalizedDescription", "LocalizedAbbreviation", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '3b12121b-c257-461f-8532-795c7e32266b', 0, 'contentitemgroup-unknown', 'Unknown', 'Represents an as yet undetermined ContentItemGroup value, often used as an initial default value or as a temporary placeholder that will be updated in the future', 'CntItmGrp Ukwn', '', -1, true, '00000000-0000-0000-0000-000000000000', 'a4531925b52941696540813f3158953a3d33c9bd6d7b02a8575627a996021d06', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, '987beea5-727c-466e-9c8a-fa0025e9498c', 0, 'contentitemgroup-none', 'None', 'Represents a value indicating that ContentItemGroup does not apply in this case, is intentionally omitted or undefined, or is not relevant', 'CntItmGrp None', '', -1, true, '00000000-0000-0000-0000-000000000000', '747eaa5f918381818151c355e46ff5e4b0ad6b1d15ebdc4a1204bc3249cfd201', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;
