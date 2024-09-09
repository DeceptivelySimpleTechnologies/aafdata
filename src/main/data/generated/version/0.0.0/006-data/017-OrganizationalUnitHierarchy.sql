-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.

-- Table: OrganizationalUnitHierarchy.OrganizationalUnitHierarchy

INSERT INTO "OrganizationalUnitHierarchy"."OrganizationalUnitHierarchy"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "ParentOrganizationalUnitId", "ChildOrganizationalUnitId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (0, '0b8342e8-7dad-41cf-92f0-8dbf79138635', 0, 'organizationalunithierarchy-none-deceptivelysimpletechnologiesinc-product', 1, 1, '', -1, true, '00000000-0000-0000-0000-000000000000', 'a9f5c350838cc1d7db5f19f90cbb9d5c7b5edc9ce34001061e512c5e4c162a79', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (1, '5cef1ffd-cc2d-480d-ab44-4617cc118f44', 0, 'organizationalunithierarchy-none-cygnussolutionsinc-service', 2, 2, '', -1, true, '00000000-0000-0000-0000-000000000000', '0ce9889f37515c9044596018fae26ed47940e1ca5de2bf56421c9f6065e8ca46', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;
