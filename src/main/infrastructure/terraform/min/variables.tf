variable "aws_region" {
  default = "us-east-2"
}

variable "application_name" {
  default = "aaf-data"
}

variable "existing_domain_name" {
  default = "example.com"
}

variable "environment_name" {
  default = "min"
}

variable "key_name" {
  description = "The name of the key pair to use for the instance"
  type        = "ec2-login"
}