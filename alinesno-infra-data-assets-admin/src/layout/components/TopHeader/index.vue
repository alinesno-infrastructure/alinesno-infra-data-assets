<template>
  <div class="top-headers">
      <header-banner />
      <header-app/>
      <header-nav />
  </div>
</template>

<script>
  import HeaderApp from './application'
  import HeaderBanner from './banner'
  import HeaderNav from './nav'

  export default {
    name: 'TopHeader',
    components: {
        HeaderApp ,
        HeaderBanner ,
        HeaderNav ,
    },
  }
</script>

