-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: Person.Person

INSERT INTO "Person"."Person"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LegalGivenName1", "LegalSurname1", "BornAtDateTimeUtc", "LegalCitizenOfCountryGeographicUnitId", "LocaleId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '9b0f8498-3d7f-4e71-a233-6c7bd2812051', 0, 'person-none-unknown-unknown', 'Unknown', 'Unknown', '2022-06-01 00:00:00.000', 0, 0, '', -1, true, '00000000-0000-0000-0000-000000000000', 'dc38239a3c7ce6eb5b4a17b028b55b2efc1e82294fbd61b330297beb4df625f3', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, 'a1fab3d0-ea4d-4b06-b5ff-056ecbc4afea', 0, 'person-none-none-none', 'None', 'None', '2022-06-01 00:00:00.000', 0, 0, '', -1, true, '00000000-0000-0000-0000-000000000000', '65474719bf59fc42af2cb68b270adf83d8f6c1679cee0775822c7915caa8fcce', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (1, '385d6088-a1a9-4012-8a37-691863bb3e2d', 0, 'person-none-amy-anderson', 'Amy', 'Anderson', '2002-01-02 10:09:08.765', 1, 1, '', -1, true, '00000000-0000-0000-0000-000000000000', 'b7ab7f39ba7d9c48ed056838304de43092e0d7992bd252349f56afb113d050ac', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;