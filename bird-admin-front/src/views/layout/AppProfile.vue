<template>
	<div class="layout-profile">
		<div>
			<img :src="this.icon" alt="" />
		</div>
		<button class="p-link layout-profile-link" @click="onClick">
			<span class="username">{{this.name}}</span>
			<i class="pi pi-fw pi-cog"></i>
		</button>
        <transition name="layout-submenu-wrapper">
            <ul v-show="expanded">
                <li><button class="p-link"><i class="pi pi-fw pi-user"></i><span>Account</span></button></li>
                <li><button class="p-link"><i class="pi pi-fw pi-inbox"></i><span>Notifications</span><span class="menuitem-badge">2</span></button></li>
                <li><button class="p-link" @click="logout"><i class="pi pi-fw pi-power-off"></i><span>Logout</span></button></li>
            </ul>
        </transition>

	</div>
</template>

<script>
  export default {
		data() {
			return {
        name:null,
        icon:null,
				expanded: false
			}
		},
    created() {
      this.name=this.$store.getters.name,
       this.icon=this.$store.getters.icon
    },
    mounted() {

    },

		methods: {
			onClick(event){
				this.expanded = !this.expanded;
				event.preventDefault();
			},
      logout() {
        this.$store.dispatch('LogOut').then(() => {
          location.reload()// In order to re-instantiate the vue-router object to avoid bugs
        })
        this.$router.push("/login")
      }
		}
	}
</script>

<style scoped>

</style>
