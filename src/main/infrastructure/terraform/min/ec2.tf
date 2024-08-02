resource "aws_cloudwatch_log_group" "this" {
  name              = "/aws/ecs/${local.application}"
  retention_in_days = 7

  tags = local.tags
}
