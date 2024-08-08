-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- Table: LegalEntity.LegalEntity

-- DROP TABLE "LegalEntity"."LegalEntity";

CREATE TABLE "LegalEntity"."LegalEntity"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(100) COLLATE pg_catalog."default" NOT NULL,

    "OrganizationalUnitId" bigint NOT NULL,
    "PersonId" bigint NOT NULL,
    "LocalizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,

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

    CONSTRAINT "LegalEntity_PK" PRIMARY KEY ("Id")

    CONSTRAINT "LegalEntity_CHK_TextKey" CHECK ("TextKey" ~* "^[a-z0-9-]+$")
    CONSTRAINT "LegalEntity_CHK_LocalizedName" CHECK ("LocalizedName" ~* "^[A-Za-z]+$")

    CONSTRAINT "LegalEntity_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc")
    CONSTRAINT "LegalEntity_UQ1_OrganizationalUnitId_PersonId_DeletedAtDateTimeUtc" UNIQUE ("OrganizationalUnitId", "PersonId", "DeletedAtDateTimeUtc")
    CONSTRAINT "LegalEntity_UQ1_LocalizedName_DeletedAtDateTimeUtc" UNIQUE ("LocalizedName", "DeletedAtDateTimeUtc")

    CONSTRAINT "LegalEntity_FK_EntitySubtypeId" FOREIGN KEY ("Id") REFERENCES "EntitySubtype"("Id")
    CONSTRAINT "LegalEntity_FK_OrganizationalUnitId" FOREIGN KEY ("Id") REFERENCES "OrganizationalUnit"("Id")
    CONSTRAINT "LegalEntity_FK_PersonId" FOREIGN KEY ("Id") REFERENCES "Person"("Id")
)

    TABLESPACE pg_default;

ALTER TABLE "LegalEntity"."LegalEntity"
    OWNER to "AafCorePublisher";