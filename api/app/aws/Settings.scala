package io.flow.delta.aws

/**
* Single place to find all the AWS-related configuration settings.
* Make it easy to change (or fix) in one place.
**/
trait Settings {

  // Length of time in seconds after a new Amazon EC2 instance comes into service that Auto Scaling starts checking its health
  val asgHealthCheckGracePeriod = 300

  // The minimum size of the Auto Scaling group
  val asgMinSize = 3

  // The maximum size of the Auto Scaling group
  val asgMaxSize = 3

  // The number of Amazon EC2 instances that should be running in the autoscaling group
  val asgDesiredSize = 3

  // Subnets for the load balancer and autoscaling group instances
  val subnets = Seq("subnet-719c7029", "subnet-3538f243", "subnet-2338f255", "subnet-6c9c7034")

  // Keypair name used to SSH into EC2 instances created by the autoscaling group
  val ec2KeyName = "flow-admin"

  // Launch configuration image ID. AMI instance is: Amazon Linux AMI for ECS
  val launchConfigImageId = "ami-9886a0f2"

  // Role for the new launch configuration
  val launchConfigIamInstanceProfile = "ecsInstanceRole"

  // Security groups for the EC2 instances launch configuration and autoscaling group
  val securityGroups = Seq("sg-47fb1b3e")

  // Should this be higher? And if so, probably should use something other than t2.micro
  val containerMemory = 1024

  // Launch configuration EC2 instance type. t2 micro for now, but probably change later or make configurable
  val launchConfigInstanceType = "t2.micro"

  // EC2 service role
  val serviceRole = "ecsServiceRole"

  // When a new service is created, explicitly set the desired count to 0, then scale up to 3
  val createServiceDesiredCount = 0
  val maxScaleUpDesiredCount = 3

}
