-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: GeographicUnitHierarchy.GeographicUnitHierarchy

INSERT INTO "GeographicUnitHierarchy"."GeographicUnitHierarchy"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "ParentGeographicUnitId", "ChildGeographicUnitId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (0, '5ccf79b3-cb67-4818-a99f-a23a9e50359e', 0, 'geographicunithierarchy-none-unitedstatesofamerica-unitedstatesofamerica', 1, 1, '', -1, true, '00000000-0000-0000-0000-000000000000', '5ef9cd777b7ccfed02e1ef549d6b092a60546cafa7fc0796f7dacee3cef595bd', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (1, 'd4a377af-23de-452b-ad1e-3b439d911bd9', 0, 'geographicunithierarchy-none-unitedstatesofamerica-texas', 1, 2, '', -1, true, '00000000-0000-0000-0000-000000000000', 'f7d638bd5ca3aadfa61709064b5de19d7cddb78cd941dcc374ba88d53276de70', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;