-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- Table: Organization.Organization

-- DROP TABLE "Organization"."Organization";

CREATE TABLE "Organization"."Organization"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "LegalName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,
    "DoingBusinessAs" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LegalFormationAtDateTimeUtc" timestamp without time zone NOT NULL,

    "ResourceName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "Ordinal" bigint NOT NULL,
    "IsActive" boolean NOT NULL,
    "CorrelationUuid" uuid NOT NULL,
    "Digest" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "CreatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "CreatedByInformationSystemUserId" bigint NOT NULL,
    "UpdatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "UpdatedByInformationSystemUserId" bigint NOT NULL,
    "DeletedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "DeletedByInformationSystemUserId" bigint NOT NULL,

    CONSTRAINT "Organization_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "Organization_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),
    CONSTRAINT "Organization_CHK_LegalName" CHECK ("LegalName" ~* '^[A-Za-z,\.,!?\/ ]+$'),

    CONSTRAINT "Organization_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "Organization_UQ1_LegalName_DeletedAtDateTimeUtc" UNIQUE ("LegalName", "DeletedAtDateTimeUtc"),

    CONSTRAINT "Organization_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "Organization_IDX_LegalName" ON "Organization"."Organization" ("LegalName")
