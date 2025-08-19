-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: ContentItemGroupContentItemAssociation.ContentItemGroupContentItemAssociation

-- DROP TABLE "ContentItemGroupContentItemAssociation"."ContentItemGroupContentItemAssociation";

CREATE TABLE "ContentItemGroupContentItemAssociation"."ContentItemGroupContentItemAssociation"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "ContentItemGroupId" bigint NOT NULL,
    "ContentItemId" bigint NOT NULL,

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

    CONSTRAINT "CntItmGrpCntItmAssoc_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "CntItmGrpCntItmAssoc_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "CntItmGrpCntItmAssoc_UQ1_TextKey_DeletedAt" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "CntItmGrpCntItmAssoc_UQ1_CntItmGrpId_CntItmId_DeletedAt" UNIQUE ("ContentItemGroupId", "ContentItemId", "DeletedAtDateTimeUtc"),

    CONSTRAINT "CntItmGrpCntItmAssoc_FK_ContentItemGroup" FOREIGN KEY ("ContentItemGroupId") REFERENCES "ContentItemGroup"."ContentItemGroup"("Id"),
    CONSTRAINT "CntItmGrpCntItmAssoc_FK_ContentItem" FOREIGN KEY ("ContentItemId") REFERENCES "ContentItem"."ContentItem"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "CntItmGrpCntItmAssoc_IDX_ContentItemGroupId" ON "ContentItemGroupContentItemAssociation"."ContentItemGroupContentItemAssociation" ("ContentItemGroupId");
CREATE INDEX "CntItmGrpCntItmAssoc_IDX_ContentItemId" ON "ContentItemGroupContentItemAssociation"."ContentItemGroupContentItemAssociation" ("ContentItemId")
