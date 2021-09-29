License This project is licensed under Apache 2.0 license.

# kBundle (ANDROID)

[![](https://jitpack.io/v/hanibalsk/kbundle.svg)](https://jitpack.io/#hanibalsk/kbundle) [![Android CI](https://github.com/hanibalsk/kbundle/actions/workflows/android.yml/badge.svg)](https://github.com/hanibalsk/kbundle/actions/workflows/android.yml) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/sk.32bit/kbundle/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/sk.32bit/kbundle)

Better, type safe version of creating bundle.

### Gradle

```gradle
implementation "sk.32bit:kbundle:0.2.0"
```

### Usage

## Bundle creation

```gradle
val bundle = bundle {
  "IntKey" to 4
  "LongKey" to 7L
  "StringKey" to "Some text"
  putAll(existingBundle) // Adds values from existing bundle
}
```

```gradle
val bundle = bundle(existingBundle) {
  "IntKey" to 4
  "LongKey" to 7L
  "StringKey" to "Some text"
}
```

## Persistable bundle creation

```gradle
val persistableBundle = persistableBundle(existingBundle) {
  "IntKey" to 4
  "LongKey" to 7L
  "StringKey" to "Some text"
}
```

### Fragment with bundle arguments

```gradle
class CustomFragment : Fragment() {

  private val intParameter by requireArgument<Int>()
  private val stringWithDefaultParameter by requireArgument<String>("default text")
  private var longOptionalParameter by instanceArgument<Long>()

  // Error throw, when missing
  private val parcelableParameter by requireArgument<Rect>("parcelableParameter parameter is required")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    println("intParameter is $intParameter")
    println("stringWithDefaultParameter is $stringWithDefaultParameter")
    println("longOptionalParameter is $longOptionalParameter")
    println("parcelableParameter is $parcelableParameter")

    // Parameter can be changed
    longOptionalParameter = 22L
  }

  companion object {

    fun newInstance(
      intParameter: Int,
      stringWithDefaultParameter: String,
      parcelableParameter: Rect,
      longOptionalParameter: Long? = null,
    ) = CustomFragment().also { fragment ->
      fragment.arguments = bundle {
        fragment::intParameter.name to intParameter
        fragment::stringWithDefaultParameter.name to stringWithDefaultParameter
        fragment::longOptionalParameter.name to longOptionalParameter
        fragment::parcelableParameter.name to parcelableParameter
      }
    }
  }
}
```

### Code Formatting

```gradle
./gradlew spotlessApply
```

*Make sure you update [spotless.license.kt](spotless.license.kt) and [LICENSE.md](LICENSE.md) to reflect your own
license and author info!* Other settings for this plugin can be tweaked
in [gradle/spotless_plugin_config.gradle](gradle/spotless_plugin_config.gradle).

### Check if Dependencies Are Up-to-Date

```gradle
./gradlew dependencyUpdates
```

Settings can be tweaked for this plugin
within [gradle/versions_plugin_config.gradle](gradle/versions_plugin_config.gradle).

### Publishing the Library to Bintray -> jCenter

This repository is setup to automatically publish to Bintray when you create a new release from GitHub.

But there are some pre-requisites:

1. Update [library_info.gradle](library_info.gradle) to contain your library's information (group, description, etc.).
2. From your GitHub repository settings, add **secrets** for `BINTRAY_ORG`, `BINTRAY_USER`, and `BINTRAY_API_KEY`. These
   will be used by the `Bintray Release` GitHub Actions workflow.
3. With each release, update the version name and code in [library_info.gradle](library_info.gradle#L6-L8).
4. Once you've deployed to Bintray for the first time, you can link your Bintray repository to jCenter so people can
   depend on your library from Android projects _without_ having to add any special Maven repositories.

How does releasing work?

1. **Automatically** when you create
   a [Release](https://help.github.com/en/github/administering-a-repository/releasing-projects-on-github)
   in your GitHub repository.
2. OR if you set the three environment variables mentioned above, and invoke `./gradlew bintrayUpload`.
