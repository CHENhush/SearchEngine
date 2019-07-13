<template>
    <div id="app">
        <router-view/>
    </div>
</template>

<style lang="less">
// -------------------
// normalize
// -------------------
* {
    padding: 0;
    margin: 0;
}
a {
    text-decoration: none;
}

li {
    list-style-type: none;
}

img,
input,
button {
    outline: none;
    border: none;
}

button {
    cursor: pointer;
}

.clearfix {
    zoom: 1;
}
.clearfix:after {
    content: "\0020";
    display: block;
    clear: both;
    height: 0;
}

em, i {
    font-style: normal;
}


// -----------------
// common
// --------------

.wrap {
    margin: 0 auto;
    width: 1160px;
}

body {
    color: #333;
}

.p-ellipsis {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>
