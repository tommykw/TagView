# TagView
![Bitrise](https://www.bitrise.io/app/17ab805de3d8867f/status.svg?token=z-q3QcMv6DCePToEScbQpA&branch=master)
[![Jitpack](https://jitpack.io/v/tommykw/TagView.svg)](https://jitpack.io/#tommykw/TagView)

Android library for creating simple tag view.

<img src=https://raw.githubusercontent.com/tommykw/TagView/master/capture/tag.png>

## TagView has
- Editable style of text, font and color.
- Listener of tag selecting.

## Usage
### 1.Set the TagView to the layout
```xml
<com.github.tommykw.tagview.TagView
    android:id="@+id/tagview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="@dimen/spacing_large"
    android:layout_marginStart="@dimen/spacing_big"
    android:gravity="center"
    app:stroke_width="1dp"
    app:stroke_color="@color/gray"
    app:background_color="@color/white"
    app:left_drawable_padding="4dp"
    app:sort_type="asc"
    app:left_drawable="@drawable/ic_launcher_background"
    app:horizontal_spacing="4dp"
    app:vertical_spacing="@dimen/spacing_small"
    app:text_font_size="6sp"
    app:corner_radius="2dp"
    app:text_color="#000000"/>
```
### 2.Implement your items
```java
List list = new ArrayList<Item>();
TagView tags = findViewById(R.id.tagview);
tags.setTags(list, new DataTransform() {
    @NotNull
    @Override
    public String transfer(Object item) {
        if (item instanceof Item) {
            return ((Item) item).getName();
        }
        return item.toString();
    }
});
tags.setClickListener(new TagView.TagClickListener() {
    @Override
    public void onTagClick(Object item) {
        if (item instanceof Item) {
            int id = ((Item) item).getId();
        }
    }
});
```

## Installation
TagView is distributed with JitPack.
First, add JitPack to your gradle repositories.

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Then add the dependency on TagView.
```
dependencies {
    compile 'com.github.tommykw:TagView:0.0.1'
}
```
