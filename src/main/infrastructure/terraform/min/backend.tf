terraform {
  backend "s3" {
    bucket = "terraform-state-bucket"
    key    = "terraform/state"
    region = var.aws_region
  }
}