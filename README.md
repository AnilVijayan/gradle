<img src="gradle.png" width="350px" alt="Gradle Logo" />

[![Revved up by Gradle Enterprise](https://img.shields.io/badge/Revved%20up%20by-Gradle%20Enterprise-06A0CE?logo=Gradle&labelColor=02303A)](https://ge.gradle.org/scans)
[![CII Best Practices](https://bestpractices.coreinfrastructure.org/projects/4898/badge)](https://bestpractices.coreinfrastructure.org/projects/4898)

[Gradle](https://gradle.org/) is a build tool with a focus on build automation and support for multi-language development. If you are building, testing, publishing, and deploying software on any platform, Gradle offers a flexible model that can support the entire development lifecycle from compiling and packaging code to publishing web sites. Gradle has been designed to support build automation across multiple languages and platforms including Java, Scala, Android, Kotlin, C/C++, and Groovy, and is closely integrated with development tools and continuous integration servers including Eclipse, IntelliJ, and Jenkins.

**For more information, please visit the [official project homepage](https://gradle.org)**

## How to configure the gradle project for ppc64le architecture

- Clone the gradle offical repository

- Checkout the respective tag ( according to the release decided to create gradle zip for) from the repository

  `Ex: git checkout -b v7.6.4 tag/v7.6.4`

- Refer the changes made on hmc_devops_ci repo and apply that to the current
  https://github.ibm.com/csnext/hmc_devops_ci/commit/92029178caf9dfd6c45c7e69a77311225e0e1f5f

- Create the zip for the gradle using,

  $ ./gradlew :distributions-full:binDistributionZip -Dorg.gradle.java.home=/usr/lib/jvm/java-11-openjdk-amd64 

  NB: During the building of 7.6.4, only after enabled lenient dependency verification the build could pass as below
      $ ./gradlew :distributions-full:binDistributionZip -Dorg.gradle.java.home=/usr/lib/jvm/java-11-openjdk-amd64 --dependenc
y-verification lenient 

- Find the gradle zip at

  $./subprojects/distributions-full/build/distributions/gradle-7.6.4-bin.zip

