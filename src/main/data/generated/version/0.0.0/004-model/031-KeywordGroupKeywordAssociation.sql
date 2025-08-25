-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: KeywordGroupKeywordAssociation.KeywordGroupKeywordAssociation

-- DROP TABLE "KeywordGroupKeywordAssociation"."KeywordGroupKeywordAssociation";

CREATE TABLE "KeywordGroupKeywordAssociation"."KeywordGroupKeywordAssociation"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "KeywordGroupId" bigint NOT NULL,
    "KeywordId" bigint NOT NULL,

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

    CONSTRAINT "KywdGrpKywdAssoc_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "KywdGrpKywdAssoc_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "KywdGrpKywdAssoc_UQ1_TextKey_DeletedAt" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "KywdGrpKywdAssoc_UQ1_KywdGrpId_KywdId_DeletedAt" UNIQUE ("KeywordGroupId", "KeywordId", "DeletedAtDateTimeUtc"),

    CONSTRAINT "KywdGrpKywdAssoc_FK_KeywordGroup" FOREIGN KEY ("KeywordGroupId") REFERENCES "KeywordGroup"."KeywordGroup"("Id"),
    CONSTRAINT "KywdGrpKywdAssoc_FK_Keyword" FOREIGN KEY ("KeywordId") REFERENCES "Keyword"."Keyword"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "KywdGrpKywdAssoc_IDX_KeywordGroupId" ON "KeywordGroupKeywordAssociation"."KeywordGroupKeywordAssociation" ("KeywordGroupId");
CREATE INDEX "KywdGrpKywdAssoc_IDX_KeywordId" ON "KeywordGroupKeywordAssociation"."KeywordGroupKeywordAssociation" ("KeywordId")
