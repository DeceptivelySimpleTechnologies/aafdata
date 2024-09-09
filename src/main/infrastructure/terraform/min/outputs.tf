output "s3_bucket_id" {
  value = aws_s3_bucket.terraform_state.id
}

output "ec2_instance_id" {
  value = aws_instance.host.id
}

output "route53_record" {
  value = aws_route53_record.min.fqdn
}