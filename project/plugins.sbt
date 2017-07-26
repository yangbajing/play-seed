logLevel := Level.Warn

resolvers += Resolver.sbtPluginRepo("releases")

addSbtPlugin("com.typesafe.play" %% "sbt-plugin" % "2.6.2")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.7.1")
