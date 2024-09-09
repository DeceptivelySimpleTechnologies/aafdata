-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.

-- Table: GeographicUnit.GeographicUnit

INSERT INTO "GeographicUnit"."GeographicUnit"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LocalizedName", "LocalizedDescription", "LocalizedAbbreviation", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '6d8cd80e-2e48-43f3-ba7b-865ea939759f', 0, 'geographicunit-unknown', 'Unknown', 'Represents an as yet undetermined language value, often used as an initial default value or as a temporary placeholder that will be updated in the future', 'Lang Ukwn', '', -1, true, '00000000-0000-0000-0000-000000000000', '967cf9d0a6831f8fa88a906da0ff7dd94c02203cc777cb11190c1a9d329295cb', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, '5ccf79b3-cb67-4818-a99f-a23a9e50359e', 0, 'geographicunit-none', 'None', 'Represents a value indicating that language does not apply in this case, is intentionally omitted or undefined, or is not relevant', 'Lang None', '', -1, true, '00000000-0000-0000-0000-000000000000', '70803e7b64abb4fca64e03495b8d4f833e61aa9f8b9d4b6a7f26519c8ce3cff0', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (1, 'd4a377af-23de-452b-ad1e-3b439d911bd9', 65, 'geographicunit-country-unitedstatesofamerica', 'United States of America', 'A federal, constitutional republic located primarily located in North America and made up of fifty states', 'USA', '', -1, true, '00000000-0000-0000-0000-000000000000', 'ec99f0e11b973e82117e49df34dcf325143d4bec836e1749577f48bec51f3ff5', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (2, '48dd25f5-af7b-48d4-9abc-ec38fb2df7d6', 66, 'geographicunit-usstate-texas', 'Texas', 'A large state in the South Central United States, bordered by Louisiana to the east, Arkansas to the northeast, Oklahoma to the north, New Mexico to the west, the country of Mexico to the south and , and the Gulf of Mexico to the southeast', 'TX', '', -1, true, '00000000-0000-0000-0000-000000000000', '7fd779182bcf3632c522b7e7d634c7f05d3016e5d129eeb1194de972d36ac013', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;