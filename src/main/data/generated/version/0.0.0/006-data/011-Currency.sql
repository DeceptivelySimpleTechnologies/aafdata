-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: Currency.Currency

INSERT INTO "Currency"."Currency"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "CountryGeographicUnitId", "LocalizedName", "LocalizedDescription", "LocalizedAbbreviation", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '5eeb90c5-3fb4-48af-9cb6-7712c3ac1b29', 0, 'currency-unknown', -1, 'Unknown', 'Represents an as yet undetermined currency value, often used as an initial default value or as a temporary placeholder that will be updated in the future', 'Loc Ukwn', '', -1, true, '00000000-0000-0000-0000-000000000000', '0b9651bd5d0531d7e14351eabcf99a4adeb52c1582d7c7645245274fb43368de', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, '6262eb6d-884c-4e46-870c-64e678f67297', 0, 'currency-none', 0, 'None', 'Represents a value indicating that currency does not apply in this case, is intentionally omitted or undefined, or is not relevant', 'Loc None', '', -1, true, '00000000-0000-0000-0000-000000000000', '40eda044dcfeb3be9abd48cf9787d62c7ddb93c9f8ea7aa53e2af6a48c358914', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (1, '625af3a4-6b43-40d4-8141-33346b3ba838', 0, 'currency-usa-dollar', 1, 'U.S. Dollar', 'The official currency of the United States of America and of several other countries', 'USD', '', -1, true, '00000000-0000-0000-0000-000000000000', 'f93bf6c1ca7a7ab527b5a4a259d4f6b5b5c7794f94b75a10c30179c8119231d6', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;