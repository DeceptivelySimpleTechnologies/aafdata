module "s3_bucket" {
  bucket = "${variables.identifier}-${variables.environment}-${random_string.this.id}"
  acl    = "private"

  force_destroy = true

  versioning = {
    enabled = false
  }

  # S3 bucket-level Public Access Block configuration
  # block_public_acls       = false
  # block_public_policy     = false
  # ignore_public_acls      = false
  # restrict_public_buckets = false

  tags = local.tags  
}
