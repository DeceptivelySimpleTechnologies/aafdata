-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: OrganizationalUnit.OrganizationalUnit

-- DROP TABLE "OrganizationalUnit"."OrganizationalUnit";

CREATE TABLE "OrganizationalUnit"."OrganizationalUnit"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "OrganizationId" bigint NOT NULL,
    "LegalName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,
    "DoingBusinessAs" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LegalFormationAtDateTimeUtc" timestamp without time zone NOT NULL,

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

    CONSTRAINT "OrganizationalUnit_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "OrganizationalUnit_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),
    CONSTRAINT "OrganizationalUnit_CHK_LegalName" CHECK ("LegalName" ~* '^[A-Za-z,\.,!?\/ ]+$'),

    CONSTRAINT "OrganizationalUnit_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "OrganizationalUnit_UQ1_LegalName_DeletedAtDateTimeUtc" UNIQUE ("LegalName", "DeletedAtDateTimeUtc"),

    CONSTRAINT "OrganizationalUnit_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "OrganizationalUnit_FK_OrganizationId" FOREIGN KEY ("OrganizationId") REFERENCES "Organization"."Organization"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "OrganizationalUnit_IDX_LegalName" ON "OrganizationalUnit"."OrganizationalUnit" ("LegalName")
