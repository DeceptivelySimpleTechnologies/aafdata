-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: OrganizationalUnitHierarchy.OrganizationalUnitHierarchy

-- DROP TABLE "OrganizationalUnitHierarchy"."OrganizationalUnitHierarchy";

CREATE TABLE "OrganizationalUnitHierarchy"."OrganizationalUnitHierarchy"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "ParentOrganizationalUnitId" bigint NOT NULL,
    "ChildOrganizationalUnitId" bigint NOT NULL,

    "ResourceName" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "Ordinal" bigint NOT NULL DEFAULT -1,
    "IsActive" boolean NOT NULL DEFAULT true,
    "CorrelationUuid" uuid NOT NULL,
    "Digest" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "CreatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "CreatedByInformationSystemUserId" bigint NOT NULL,
    "UpdatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "UpdatedByInformationSystemUserId" bigint NOT NULL,
    "DeletedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "DeletedByInformationSystemUserId" bigint NOT NULL,

    CONSTRAINT "OrganizationalUnitHierarchy_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "OrganizationalUnitHierarchy_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "OrganizationalUnitHierarchy_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "OrganizationalUnitHierarchy_UQ1_ParentOrganizationalUnitId_ChildOrganizationalUnitId_DeletedAtDateTimeUtc" UNIQUE ("ParentOrganizationalUnitId", "ChildOrganizationalUnitId", "DeletedAtDateTimeUtc"),

    CONSTRAINT "OrganizationalUnitHierarchy_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "OrganizationalUnitHierarchy_FK_ParentOrganizationalUnitId" FOREIGN KEY ("ParentOrganizationalUnitId") REFERENCES "OrganizationalUnit"."OrganizationalUnit"("Id"),
    CONSTRAINT "OrganizationalUnitHierarchy_FK_ChildOrganizationalUnitId" FOREIGN KEY ("ChildOrganizationalUnitId") REFERENCES "OrganizationalUnit"."OrganizationalUnit"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "OrganizationalUnitHierarchy_IDX_ParentOrganizationalUnitId" ON "OrganizationalUnitHierarchy"."OrganizationalUnitHierarchy" ("ParentOrganizationalUnitId");
CREATE INDEX "OrganizationalUnitHierarchy_IDX_ChildOrganizationalUnitId" ON "OrganizationalUnitHierarchy"."OrganizationalUnitHierarchy" ("ChildOrganizationalUnitId")
