/**
 * Generated by apidoc - http://www.apidoc.me
 * Service version: 0.1.68
 * apidoc:0.11.25 http://www.apidoc.me/flow/delta/0.1.68/anorm_2_x_parsers
 */
import anorm._

package io.flow.delta.v0.anorm.parsers {

  import io.flow.delta.v0.anorm.conversions.Standard._

  import io.flow.common.v0.anorm.conversions.Types._
  import io.flow.delta.config.v0.anorm.conversions.Types._
  import io.flow.delta.v0.anorm.conversions.Types._

  object DockerProvider {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(s"$prefix${sep}name")

    def parser(name: String = "docker_provider"): RowParser[io.flow.delta.v0.models.DockerProvider] = {
      SqlParser.str(name) map {
        case value => io.flow.delta.v0.models.DockerProvider(value)
      }
    }

  }

  object EventType {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(s"$prefix${sep}name")

    def parser(name: String = "event_type"): RowParser[io.flow.delta.v0.models.EventType] = {
      SqlParser.str(name) map {
        case value => io.flow.delta.v0.models.EventType(value)
      }
    }

  }

  object Publication {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(s"$prefix${sep}name")

    def parser(name: String = "publication"): RowParser[io.flow.delta.v0.models.Publication] = {
      SqlParser.str(name) map {
        case value => io.flow.delta.v0.models.Publication(value)
      }
    }

  }

  object Role {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(s"$prefix${sep}name")

    def parser(name: String = "role"): RowParser[io.flow.delta.v0.models.Role] = {
      SqlParser.str(name) map {
        case value => io.flow.delta.v0.models.Role(value)
      }
    }

  }

  object Scms {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(s"$prefix${sep}name")

    def parser(name: String = "scms"): RowParser[io.flow.delta.v0.models.Scms] = {
      SqlParser.str(name) map {
        case value => io.flow.delta.v0.models.Scms(value)
      }
    }

  }

  object Status {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(s"$prefix${sep}name")

    def parser(name: String = "status"): RowParser[io.flow.delta.v0.models.Status] = {
      SqlParser.str(name) map {
        case value => io.flow.delta.v0.models.Status(value)
      }
    }

  }

  object Visibility {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(s"$prefix${sep}name")

    def parser(name: String = "visibility"): RowParser[io.flow.delta.v0.models.Visibility] = {
      SqlParser.str(name) map {
        case value => io.flow.delta.v0.models.Visibility(value)
      }
    }

  }

  object AwsActor {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id"
    )

    def parser(
      id: String = "id"
    ): RowParser[io.flow.delta.v0.models.AwsActor] = {
      SqlParser.long(id) map {
        case id => {
          io.flow.delta.v0.models.AwsActor(
            id = id
          )
        }
      }
    }

  }

  object Build {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      projectPrefix = s"$prefix${sep}project",
      status = s"$prefix${sep}status",
      name = s"$prefix${sep}name"
    )

    def parser(
      id: String = "id",
      projectPrefix: String = "project",
      status: String = "status",
      name: String = "name"
    ): RowParser[io.flow.delta.v0.models.Build] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.ProjectSummary.parserWithPrefix(projectPrefix) ~
      io.flow.delta.v0.anorm.parsers.Status.parser(status) ~
      SqlParser.str(name) map {
        case id ~ project ~ status ~ name => {
          io.flow.delta.v0.models.Build(
            id = id,
            project = project,
            status = status,
            name = name
          )
        }
      }
    }

  }

  object BuildState {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      name = s"$prefix${sep}name",
      desiredPrefix = s"$prefix${sep}desired",
      lastPrefix = s"$prefix${sep}last",
      latestImage = s"$prefix${sep}latest_image"
    )

    def parser(
      name: String = "name",
      desiredPrefix: String = "desired",
      lastPrefix: String = "last",
      latestImage: String = "latest_image"
    ): RowParser[io.flow.delta.v0.models.BuildState] = {
      SqlParser.str(name) ~
      io.flow.delta.v0.anorm.parsers.State.parserWithPrefix(desiredPrefix).? ~
      io.flow.delta.v0.anorm.parsers.State.parserWithPrefix(lastPrefix).? ~
      SqlParser.str(latestImage).? map {
        case name ~ desired ~ last ~ latestImage => {
          io.flow.delta.v0.models.BuildState(
            name = name,
            desired = desired,
            last = last,
            latestImage = latestImage
          )
        }
      }
    }

  }

  object DashboardBuild {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      projectPrefix = s"$prefix${sep}project",
      name = s"$prefix${sep}name",
      desiredPrefix = s"$prefix${sep}desired",
      lastPrefix = s"$prefix${sep}last"
    )

    def parser(
      projectPrefix: String = "project",
      name: String = "name",
      desiredPrefix: String = "desired",
      lastPrefix: String = "last"
    ): RowParser[io.flow.delta.v0.models.DashboardBuild] = {
      io.flow.delta.v0.anorm.parsers.ProjectSummary.parserWithPrefix(projectPrefix) ~
      SqlParser.str(name) ~
      io.flow.delta.v0.anorm.parsers.State.parserWithPrefix(desiredPrefix) ~
      io.flow.delta.v0.anorm.parsers.State.parserWithPrefix(lastPrefix) map {
        case project ~ name ~ desired ~ last => {
          io.flow.delta.v0.models.DashboardBuild(
            project = project,
            name = name,
            desired = desired,
            last = last
          )
        }
      }
    }

  }

  object Docker {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      provider = s"$prefix${sep}provider",
      organization = s"$prefix${sep}organization"
    )

    def parser(
      provider: String = "provider",
      organization: String = "organization"
    ): RowParser[io.flow.delta.v0.models.Docker] = {
      io.flow.delta.v0.anorm.parsers.DockerProvider.parser(provider) ~
      SqlParser.str(organization) map {
        case provider ~ organization => {
          io.flow.delta.v0.models.Docker(
            provider = provider,
            organization = organization
          )
        }
      }
    }

  }

  object Event {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      createdAt = s"$prefix${sep}created_at",
      projectPrefix = s"$prefix${sep}project",
      `type` = s"$prefix${sep}type",
      summary = s"$prefix${sep}summary",
      error = s"$prefix${sep}error"
    )

    def parser(
      id: String = "id",
      createdAt: String = "created_at",
      projectPrefix: String = "project",
      `type`: String = "type",
      summary: String = "summary",
      error: String = "error"
    ): RowParser[io.flow.delta.v0.models.Event] = {
      SqlParser.str(id) ~
      SqlParser.get[_root_.org.joda.time.DateTime](createdAt) ~
      io.flow.delta.v0.anorm.parsers.ProjectSummary.parserWithPrefix(projectPrefix) ~
      io.flow.delta.v0.anorm.parsers.EventType.parser(`type`) ~
      SqlParser.str(summary) ~
      SqlParser.str(error).? map {
        case id ~ createdAt ~ project ~ typeInstance ~ summary ~ error => {
          io.flow.delta.v0.models.Event(
            id = id,
            createdAt = createdAt,
            project = project,
            `type` = typeInstance,
            summary = summary,
            error = error
          )
        }
      }
    }

  }

  object GithubAuthenticationForm {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      code = s"$prefix${sep}code"
    )

    def parser(
      code: String = "code"
    ): RowParser[io.flow.delta.v0.models.GithubAuthenticationForm] = {
      SqlParser.str(code) map {
        case code => {
          io.flow.delta.v0.models.GithubAuthenticationForm(
            code = code
          )
        }
      }
    }

  }

  object GithubUser {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      userPrefix = s"$prefix${sep}user",
      githubUserId = s"$prefix${sep}github_user_id",
      login = s"$prefix${sep}login"
    )

    def parser(
      id: String = "id",
      userPrefix: String = "user",
      githubUserId: String = "github_user_id",
      login: String = "login"
    ): RowParser[io.flow.delta.v0.models.GithubUser] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.Reference.parserWithPrefix(userPrefix) ~
      SqlParser.long(githubUserId) ~
      SqlParser.str(login) map {
        case id ~ user ~ githubUserId ~ login => {
          io.flow.delta.v0.models.GithubUser(
            id = id,
            user = user,
            githubUserId = githubUserId,
            login = login
          )
        }
      }
    }

  }

  object GithubUserForm {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      userId = s"$prefix${sep}user_id",
      githubUserId = s"$prefix${sep}github_user_id",
      login = s"$prefix${sep}login"
    )

    def parser(
      userId: String = "user_id",
      githubUserId: String = "github_user_id",
      login: String = "login"
    ): RowParser[io.flow.delta.v0.models.GithubUserForm] = {
      SqlParser.str(userId) ~
      SqlParser.long(githubUserId) ~
      SqlParser.str(login) map {
        case userId ~ githubUserId ~ login => {
          io.flow.delta.v0.models.GithubUserForm(
            userId = userId,
            githubUserId = githubUserId,
            login = login
          )
        }
      }
    }

  }

  object GithubWebhook {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id"
    )

    def parser(
      id: String = "id"
    ): RowParser[io.flow.delta.v0.models.GithubWebhook] = {
      SqlParser.long(id) map {
        case id => {
          io.flow.delta.v0.models.GithubWebhook(
            id = id
          )
        }
      }
    }

  }

  object Image {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      buildPrefix = s"$prefix${sep}build",
      name = s"$prefix${sep}name",
      version = s"$prefix${sep}version"
    )

    def parser(
      id: String = "id",
      buildPrefix: String = "build",
      name: String = "name",
      version: String = "version"
    ): RowParser[io.flow.delta.v0.models.Image] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.Build.parserWithPrefix(buildPrefix) ~
      SqlParser.str(name) ~
      SqlParser.str(version) map {
        case id ~ build ~ name ~ version => {
          io.flow.delta.v0.models.Image(
            id = id,
            build = build,
            name = name,
            version = version
          )
        }
      }
    }

  }

  object ImageForm {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      buildId = s"$prefix${sep}build_id",
      name = s"$prefix${sep}name",
      version = s"$prefix${sep}version"
    )

    def parser(
      buildId: String = "build_id",
      name: String = "name",
      version: String = "version"
    ): RowParser[io.flow.delta.v0.models.ImageForm] = {
      SqlParser.str(buildId) ~
      SqlParser.str(name) ~
      SqlParser.str(version) map {
        case buildId ~ name ~ version => {
          io.flow.delta.v0.models.ImageForm(
            buildId = buildId,
            name = name,
            version = version
          )
        }
      }
    }

  }

  object Item {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      organizationPrefix = s"$prefix${sep}organization",
      visibility = s"$prefix${sep}visibility",
      summaryPrefix = s"$prefix${sep}summary",
      label = s"$prefix${sep}label",
      description = s"$prefix${sep}description"
    )

    def parser(
      id: String = "id",
      organizationPrefix: String = "organization",
      visibility: String = "visibility",
      summaryPrefix: String = "summary",
      label: String = "label",
      description: String = "description"
    ): RowParser[io.flow.delta.v0.models.Item] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.OrganizationSummary.parserWithPrefix(organizationPrefix) ~
      io.flow.delta.v0.anorm.parsers.Visibility.parser(visibility) ~
      io.flow.delta.v0.anorm.parsers.ItemSummary.parserWithPrefix(summaryPrefix) ~
      SqlParser.str(label) ~
      SqlParser.str(description).? map {
        case id ~ organization ~ visibility ~ summary ~ label ~ description => {
          io.flow.delta.v0.models.Item(
            id = id,
            organization = organization,
            visibility = visibility,
            summary = summary,
            label = label,
            description = description
          )
        }
      }
    }

  }

  object Membership {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      userPrefix = s"$prefix${sep}user",
      organizationPrefix = s"$prefix${sep}organization",
      role = s"$prefix${sep}role"
    )

    def parser(
      id: String = "id",
      userPrefix: String = "user",
      organizationPrefix: String = "organization",
      role: String = "role"
    ): RowParser[io.flow.delta.v0.models.Membership] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.UserSummary.parserWithPrefix(userPrefix) ~
      io.flow.delta.v0.anorm.parsers.OrganizationSummary.parserWithPrefix(organizationPrefix) ~
      io.flow.delta.v0.anorm.parsers.Role.parser(role) map {
        case id ~ user ~ organization ~ role => {
          io.flow.delta.v0.models.Membership(
            id = id,
            user = user,
            organization = organization,
            role = role
          )
        }
      }
    }

  }

  object MembershipForm {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      userId = s"$prefix${sep}user_id",
      organization = s"$prefix${sep}organization",
      role = s"$prefix${sep}role"
    )

    def parser(
      userId: String = "user_id",
      organization: String = "organization",
      role: String = "role"
    ): RowParser[io.flow.delta.v0.models.MembershipForm] = {
      SqlParser.str(userId) ~
      SqlParser.str(organization) ~
      io.flow.delta.v0.anorm.parsers.Role.parser(role) map {
        case userId ~ organization ~ role => {
          io.flow.delta.v0.models.MembershipForm(
            userId = userId,
            organization = organization,
            role = role
          )
        }
      }
    }

  }

  object Organization {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      userPrefix = s"$prefix${sep}user",
      dockerPrefix = s"$prefix${sep}docker"
    )

    def parser(
      id: String = "id",
      userPrefix: String = "user",
      dockerPrefix: String = "docker"
    ): RowParser[io.flow.delta.v0.models.Organization] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.UserSummary.parserWithPrefix(userPrefix) ~
      io.flow.delta.v0.anorm.parsers.Docker.parserWithPrefix(dockerPrefix) map {
        case id ~ user ~ docker => {
          io.flow.delta.v0.models.Organization(
            id = id,
            user = user,
            docker = docker
          )
        }
      }
    }

  }

  object OrganizationForm {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      dockerPrefix = s"$prefix${sep}docker"
    )

    def parser(
      id: String = "id",
      dockerPrefix: String = "docker"
    ): RowParser[io.flow.delta.v0.models.OrganizationForm] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.Docker.parserWithPrefix(dockerPrefix) map {
        case id ~ docker => {
          io.flow.delta.v0.models.OrganizationForm(
            id = id,
            docker = docker
          )
        }
      }
    }

  }

  object OrganizationSummary {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id"
    )

    def parser(
      id: String = "id"
    ): RowParser[io.flow.delta.v0.models.OrganizationSummary] = {
      SqlParser.str(id) map {
        case id => {
          io.flow.delta.v0.models.OrganizationSummary(
            id = id
          )
        }
      }
    }

  }

  object Project {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      organizationPrefix = s"$prefix${sep}organization",
      userPrefix = s"$prefix${sep}user",
      visibility = s"$prefix${sep}visibility",
      scms = s"$prefix${sep}scms",
      name = s"$prefix${sep}name",
      uri = s"$prefix${sep}uri",
      configPrefix = s"$prefix${sep}config"
    )

    def parser(
      id: String = "id",
      organizationPrefix: String = "organization",
      userPrefix: String = "user",
      visibility: String = "visibility",
      scms: String = "scms",
      name: String = "name",
      uri: String = "uri",
      configPrefix: String = "config"
    ): RowParser[io.flow.delta.v0.models.Project] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.OrganizationSummary.parserWithPrefix(organizationPrefix) ~
      io.flow.delta.v0.anorm.parsers.Reference.parserWithPrefix(userPrefix) ~
      io.flow.delta.v0.anorm.parsers.Visibility.parser(visibility) ~
      io.flow.delta.v0.anorm.parsers.Scms.parser(scms) ~
      SqlParser.str(name) ~
      SqlParser.str(uri) ~
      io.flow.delta.config.v0.anorm.parsers.Config.parserWithPrefix(configPrefix) map {
        case id ~ organization ~ user ~ visibility ~ scms ~ name ~ uri ~ config => {
          io.flow.delta.v0.models.Project(
            id = id,
            organization = organization,
            user = user,
            visibility = visibility,
            scms = scms,
            name = name,
            uri = uri,
            config = config
          )
        }
      }
    }

  }

  object ProjectForm {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      organization = s"$prefix${sep}organization",
      name = s"$prefix${sep}name",
      visibility = s"$prefix${sep}visibility",
      scms = s"$prefix${sep}scms",
      uri = s"$prefix${sep}uri",
      configPrefix = s"$prefix${sep}config"
    )

    def parser(
      organization: String = "organization",
      name: String = "name",
      visibility: String = "visibility",
      scms: String = "scms",
      uri: String = "uri",
      configPrefix: String = "config"
    ): RowParser[io.flow.delta.v0.models.ProjectForm] = {
      SqlParser.str(organization) ~
      SqlParser.str(name) ~
      io.flow.delta.v0.anorm.parsers.Visibility.parser(visibility) ~
      io.flow.delta.v0.anorm.parsers.Scms.parser(scms) ~
      SqlParser.str(uri) ~
      io.flow.delta.config.v0.anorm.parsers.ConfigProject.parserWithPrefix(configPrefix).? map {
        case organization ~ name ~ visibility ~ scms ~ uri ~ config => {
          io.flow.delta.v0.models.ProjectForm(
            organization = organization,
            name = name,
            visibility = visibility,
            scms = scms,
            uri = uri,
            config = config
          )
        }
      }
    }

  }

  object ProjectSummary {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      organizationPrefix = s"$prefix${sep}organization",
      name = s"$prefix${sep}name",
      uri = s"$prefix${sep}uri"
    )

    def parser(
      id: String = "id",
      organizationPrefix: String = "organization",
      name: String = "name",
      uri: String = "uri"
    ): RowParser[io.flow.delta.v0.models.ProjectSummary] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.OrganizationSummary.parserWithPrefix(organizationPrefix) ~
      SqlParser.str(name) ~
      SqlParser.str(uri) map {
        case id ~ organization ~ name ~ uri => {
          io.flow.delta.v0.models.ProjectSummary(
            id = id,
            organization = organization,
            name = name,
            uri = uri
          )
        }
      }
    }

  }

  object Reference {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id"
    )

    def parser(
      id: String = "id"
    ): RowParser[io.flow.delta.v0.models.Reference] = {
      SqlParser.str(id) map {
        case id => {
          io.flow.delta.v0.models.Reference(
            id = id
          )
        }
      }
    }

  }

  object Repository {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      name = s"$prefix${sep}name",
      visibility = s"$prefix${sep}visibility",
      uri = s"$prefix${sep}uri"
    )

    def parser(
      name: String = "name",
      visibility: String = "visibility",
      uri: String = "uri"
    ): RowParser[io.flow.delta.v0.models.Repository] = {
      SqlParser.str(name) ~
      io.flow.delta.v0.anorm.parsers.Visibility.parser(visibility) ~
      SqlParser.str(uri) map {
        case name ~ visibility ~ uri => {
          io.flow.delta.v0.models.Repository(
            name = name,
            visibility = visibility,
            uri = uri
          )
        }
      }
    }

  }

  object Sha {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      projectPrefix = s"$prefix${sep}project",
      createdAt = s"$prefix${sep}created_at",
      branch = s"$prefix${sep}branch",
      hash = s"$prefix${sep}hash"
    )

    def parser(
      id: String = "id",
      projectPrefix: String = "project",
      createdAt: String = "created_at",
      branch: String = "branch",
      hash: String = "hash"
    ): RowParser[io.flow.delta.v0.models.Sha] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.ProjectSummary.parserWithPrefix(projectPrefix) ~
      SqlParser.get[_root_.org.joda.time.DateTime](createdAt) ~
      SqlParser.str(branch) ~
      SqlParser.str(hash) map {
        case id ~ project ~ createdAt ~ branch ~ hash => {
          io.flow.delta.v0.models.Sha(
            id = id,
            project = project,
            createdAt = createdAt,
            branch = branch,
            hash = hash
          )
        }
      }
    }

  }

  object State {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      timestamp = s"$prefix${sep}timestamp",
      versions = s"$prefix${sep}versions"
    )

    def parser(
      timestamp: String = "timestamp",
      versions: String = "versions"
    ): RowParser[io.flow.delta.v0.models.State] = {
      SqlParser.get[_root_.org.joda.time.DateTime](timestamp) ~
      SqlParser.get[Seq[io.flow.delta.v0.models.Version]](versions) map {
        case timestamp ~ versions => {
          io.flow.delta.v0.models.State(
            timestamp = timestamp,
            versions = versions
          )
        }
      }
    }

  }

  object StateForm {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      versions = s"$prefix${sep}versions"
    )

    def parser(
      versions: String = "versions"
    ): RowParser[io.flow.delta.v0.models.StateForm] = {
      SqlParser.get[Seq[io.flow.delta.v0.models.Version]](versions) map {
        case versions => {
          io.flow.delta.v0.models.StateForm(
            versions = versions
          )
        }
      }
    }

  }

  object Subscription {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      userPrefix = s"$prefix${sep}user",
      publication = s"$prefix${sep}publication"
    )

    def parser(
      id: String = "id",
      userPrefix: String = "user",
      publication: String = "publication"
    ): RowParser[io.flow.delta.v0.models.Subscription] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.Reference.parserWithPrefix(userPrefix) ~
      io.flow.delta.v0.anorm.parsers.Publication.parser(publication) map {
        case id ~ user ~ publication => {
          io.flow.delta.v0.models.Subscription(
            id = id,
            user = user,
            publication = publication
          )
        }
      }
    }

  }

  object SubscriptionForm {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      userId = s"$prefix${sep}user_id",
      publication = s"$prefix${sep}publication"
    )

    def parser(
      userId: String = "user_id",
      publication: String = "publication"
    ): RowParser[io.flow.delta.v0.models.SubscriptionForm] = {
      SqlParser.str(userId) ~
      io.flow.delta.v0.anorm.parsers.Publication.parser(publication) map {
        case userId ~ publication => {
          io.flow.delta.v0.models.SubscriptionForm(
            userId = userId,
            publication = publication
          )
        }
      }
    }

  }

  object Tag {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      projectPrefix = s"$prefix${sep}project",
      createdAt = s"$prefix${sep}created_at",
      name = s"$prefix${sep}name",
      hash = s"$prefix${sep}hash"
    )

    def parser(
      id: String = "id",
      projectPrefix: String = "project",
      createdAt: String = "created_at",
      name: String = "name",
      hash: String = "hash"
    ): RowParser[io.flow.delta.v0.models.Tag] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.ProjectSummary.parserWithPrefix(projectPrefix) ~
      SqlParser.get[_root_.org.joda.time.DateTime](createdAt) ~
      SqlParser.str(name) ~
      SqlParser.str(hash) map {
        case id ~ project ~ createdAt ~ name ~ hash => {
          io.flow.delta.v0.models.Tag(
            id = id,
            project = project,
            createdAt = createdAt,
            name = name,
            hash = hash
          )
        }
      }
    }

  }

  object Token {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      userPrefix = s"$prefix${sep}user",
      masked = s"$prefix${sep}masked",
      cleartext = s"$prefix${sep}cleartext",
      description = s"$prefix${sep}description"
    )

    def parser(
      id: String = "id",
      userPrefix: String = "user",
      masked: String = "masked",
      cleartext: String = "cleartext",
      description: String = "description"
    ): RowParser[io.flow.delta.v0.models.Token] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.Reference.parserWithPrefix(userPrefix) ~
      SqlParser.str(masked) ~
      SqlParser.str(cleartext).? ~
      SqlParser.str(description).? map {
        case id ~ user ~ masked ~ cleartext ~ description => {
          io.flow.delta.v0.models.Token(
            id = id,
            user = user,
            masked = masked,
            cleartext = cleartext,
            description = description
          )
        }
      }
    }

  }

  object TokenForm {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      userId = s"$prefix${sep}user_id",
      description = s"$prefix${sep}description"
    )

    def parser(
      userId: String = "user_id",
      description: String = "description"
    ): RowParser[io.flow.delta.v0.models.TokenForm] = {
      SqlParser.str(userId) ~
      SqlParser.str(description).? map {
        case userId ~ description => {
          io.flow.delta.v0.models.TokenForm(
            userId = userId,
            description = description
          )
        }
      }
    }

  }

  object UserForm {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      email = s"$prefix${sep}email",
      namePrefix = s"$prefix${sep}name"
    )

    def parser(
      email: String = "email",
      namePrefix: String = "name"
    ): RowParser[io.flow.delta.v0.models.UserForm] = {
      SqlParser.str(email).? ~
      io.flow.common.v0.anorm.parsers.Name.parserWithPrefix(namePrefix).? map {
        case email ~ name => {
          io.flow.delta.v0.models.UserForm(
            email = email,
            name = name
          )
        }
      }
    }

  }

  object UserIdentifier {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      userPrefix = s"$prefix${sep}user",
      value = s"$prefix${sep}value"
    )

    def parser(
      id: String = "id",
      userPrefix: String = "user",
      value: String = "value"
    ): RowParser[io.flow.delta.v0.models.UserIdentifier] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.Reference.parserWithPrefix(userPrefix) ~
      SqlParser.str(value) map {
        case id ~ user ~ value => {
          io.flow.delta.v0.models.UserIdentifier(
            id = id,
            user = user,
            value = value
          )
        }
      }
    }

  }

  object UserSummary {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      email = s"$prefix${sep}email",
      namePrefix = s"$prefix${sep}name"
    )

    def parser(
      id: String = "id",
      email: String = "email",
      namePrefix: String = "name"
    ): RowParser[io.flow.delta.v0.models.UserSummary] = {
      SqlParser.str(id) ~
      SqlParser.str(email).? ~
      io.flow.common.v0.anorm.parsers.Name.parserWithPrefix(namePrefix) map {
        case id ~ email ~ name => {
          io.flow.delta.v0.models.UserSummary(
            id = id,
            email = email,
            name = name
          )
        }
      }
    }

  }

  object UsernamePassword {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      username = s"$prefix${sep}username",
      password = s"$prefix${sep}password"
    )

    def parser(
      username: String = "username",
      password: String = "password"
    ): RowParser[io.flow.delta.v0.models.UsernamePassword] = {
      SqlParser.str(username) ~
      SqlParser.str(password).? map {
        case username ~ password => {
          io.flow.delta.v0.models.UsernamePassword(
            username = username,
            password = password
          )
        }
      }
    }

  }

  object Variable {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      id = s"$prefix${sep}id",
      organizationPrefix = s"$prefix${sep}organization",
      key = s"$prefix${sep}key",
      value = s"$prefix${sep}value"
    )

    def parser(
      id: String = "id",
      organizationPrefix: String = "organization",
      key: String = "key",
      value: String = "value"
    ): RowParser[io.flow.delta.v0.models.Variable] = {
      SqlParser.str(id) ~
      io.flow.delta.v0.anorm.parsers.OrganizationSummary.parserWithPrefix(organizationPrefix) ~
      SqlParser.str(key) ~
      SqlParser.str(value) map {
        case id ~ organization ~ key ~ value => {
          io.flow.delta.v0.models.Variable(
            id = id,
            organization = organization,
            key = key,
            value = value
          )
        }
      }
    }

  }

  object VariableForm {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      organization = s"$prefix${sep}organization",
      key = s"$prefix${sep}key",
      value = s"$prefix${sep}value"
    )

    def parser(
      organization: String = "organization",
      key: String = "key",
      value: String = "value"
    ): RowParser[io.flow.delta.v0.models.VariableForm] = {
      SqlParser.str(organization) ~
      SqlParser.str(key) ~
      SqlParser.str(value) map {
        case organization ~ key ~ value => {
          io.flow.delta.v0.models.VariableForm(
            organization = organization,
            key = key,
            value = value
          )
        }
      }
    }

  }

  object Version {

    def parserWithPrefix(prefix: String, sep: String = "_") = parser(
      name = s"$prefix${sep}name",
      instances = s"$prefix${sep}instances"
    )

    def parser(
      name: String = "name",
      instances: String = "instances"
    ): RowParser[io.flow.delta.v0.models.Version] = {
      SqlParser.str(name) ~
      SqlParser.long(instances) map {
        case name ~ instances => {
          io.flow.delta.v0.models.Version(
            name = name,
            instances = instances
          )
        }
      }
    }

  }

  object ItemSummary {

    def parserWithPrefix(prefix: String, sep: String = "_") = {
      io.flow.delta.v0.anorm.parsers.ProjectSummary.parserWithPrefix(prefix, sep)
    }

    def parser() = {
      io.flow.delta.v0.anorm.parsers.ProjectSummary.parser()
    }

  }

}