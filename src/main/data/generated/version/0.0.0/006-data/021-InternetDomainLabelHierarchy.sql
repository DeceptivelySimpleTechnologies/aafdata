-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: InternetDomainLabelHierarchy.InternetDomainLabelHierarchy

INSERT INTO "InternetDomainLabelHierarchy"."InternetDomainLabelHierarchy"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "ParentInternetDomainLabelId", "ChildInternetDomainLabelId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '0d82ce01-5932-4532-9901-debc4fefe283', 0, 'internetdomainlabelhierarchy-unknown-com', 1, -1, '', -1, true, '00000000-0000-0000-0000-000000000000', 'c91ff5a41ea6dafd076c40e30dbfed4acbce85420a8feac0403a4b7ac3905d14', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, '6a4afd59-e45f-4cb1-aff7-dad407f1b119', 0, 'internetdomainlabelhierarchy-none-com', 1, 0, '', -1, true, '00000000-0000-0000-0000-000000000000', '35d8308b87bdc17d45ac7557f3f882e522d13ab27dc2f8f59501fbd982edda75', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (1, 'fad5d711-21f1-4916-af5f-667fdb00433d', 0, 'internetdomainlabelhierarchy-com-deceptivelysimpletechnologies', 1, 2, '', -1, true, '00000000-0000-0000-0000-000000000000', 'f627c45a927607b51fcc0e7dabfd83e03a2a12e403edbc9d670e792ab4eee1e5', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (2, 'e22fa808-a1e0-433e-8b77-0f8dfc7de2dc', 0, 'internetdomainlabelhierarchy-com-cygnustechnologyservices', 1, 3, '', -1, true, '00000000-0000-0000-0000-000000000000', 'd4e90cc1b8236dfe14d833ca855d23457e5c95fb5e4b966491aefbeacc87c43c', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;
