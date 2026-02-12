<h1 align=center>Sweet select</h1>
<p align=center> 
    🛠️ A Compose library that makes multi-selection really easy and highly optimized, with support for a finite amount of selectable elements!
</p>

## ⬇️ Download

### 📰 Version catalog

If you're using Version Catalog, you can configure the dependency by adding it to your `libs.versions.toml` file as follows:

```toml
[versions]
#...
LIB_VERSION = "VERSION"

[libraries]
#...
LIB_NAME = { module = "LIB_MODULE", version.ref = "LIB_VERSION" }
```

### 🐘 Gradle
Add the dependency below to your **module**'s `build.gradle.kts` file:

```gradle
dependencies {
    implementation("LIB_MODULE:LIB_VERSION")
    
    // if you're using Version Catalog
    implementation(VC_REFERENCE)
}
```

## ✏️ Usage

First, create a `SweetSelectState`, this will be the main state that contains all the logic from toggling to getting selected items!

```kotlin
// Optionally, you can pass an initial list of selected items & a max number of selectable elements
val state = rememberSweetSelectState(initialSelectedItems = listOf(6,9), maxSelectable = 2008)
```

Then, all you have to do is to passa sweetClickable() modifier to your component in a lazy list as such:

```kotlin

LazyColumn {
  items(
    items = listOf(1,2,3,4,5,6,7,8,9)
  ) { item ->
      Box(
        modifier = Modifier
            .sweetClickable(
              item = item
              state = state
              onClick = { /* do something */ }
            )
      ) { /* composable */ }
    }
}
```
Optionally, if you can't or don't want to pass a .sweetClickable() modifier, you can use `SweetSelectState`'s APIs to build your own selection logic! (Read the APIs documentation to better understand how)

## 📝 Documentation
All APIs are documented below and in the code

---

`_selectedItems`

> A mutable collection of selected elements using a `Set` for optimized lookups. You can't interact with it outside the state

`selectedItems`

> A read-only and reactive `Set` of currently selected items

`isInSelectionMode`

> Returns `true` if at least one item is selected. Perfect for toggling contextual toolbars and/or styling

`isSelectionFull`

> Returns `true` if the number of selected items has reached the `maxSelectable` limit, preventing further additions

`toggle(item: T)`

> Adds or removes an item based on its current state. Returns `true` if the toggle was successful

`toggleAll(items: Collection<T>)`

> Attempts to add or remove a batch of items based on their states. It will only add items up to the `maxSelectable` cap

`isSelected(item: T)`

> A non-reactive check to see if an item is selected. Best used for one-off logic or inside event callbacks. DO NOT use directly inside a Composable or performances will take a hit

`isSelectedAsState(item: T)`

> Returns if an item is selected as a Compose `State`. This is optimized using `derivedStateOf()`

`clearSelected()`

> Clears all selected items and sets `isInSelectionMode` to false

`sweetClickable()`
> A Modifier replacement for `combinedClickable()` use it if you were using a `clickable() or combinedClickable()` modifier anyways


