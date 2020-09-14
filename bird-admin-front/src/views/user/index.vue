<template xmlns="http://www.w3.org/1999/html">
  <div class="p-grid crud-demo">
    <div class="p-col-12">
      <div class="card">
        <Toast/>
        <Toolbar class="p-mb-4">
          <template slot="left">
            <Button label="New" icon="pi pi-plus" class="p-button-success p-mr-2" @click="openNew"/>
            <Button label="Delete" icon="pi pi-trash" class="p-button-danger" @click="confirmDeleteSelected"
                    :disabled="!selectedUsers || !selectedUsers.length"/>
          </template>

          <template slot="right">
            <FileUpload mode="basic" accept="image/*" :maxFileSize="1000000" label="Import" chooseLabel="Import"
                        class="p-mr-2 p-d-inline-block"/>
            <Button label="Export" icon="pi pi-upload" class="p-button-help" @click="exportCSV($event)"/>
          </template>
        </Toolbar>

        <DataTable ref="dt" :value="users" :selection.sync="selectedUsers" dataKey="id" :paginator="true" :rows="10"
                   :filters="filters"
                   paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                   :rowsPerPageOptions="[5,10,25]"
                   currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products">
          <template #header>
            <div class="table-header">
              <h5 class="p-m-0">Manage Users</h5>
              <span class="p-input-icon-left">
                                <i class="pi pi-search"/>
                                <InputText v-model="filters['global']" placeholder="Search..."/>
                            </span>
            </div>
          </template>

          <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
          <Column field="id" header="id" sortable></Column>
          <Column field="userName" header="userName" sortable></Column>
          <Column field="passWord" header="passWord" sortable></Column>
          <Column header="icon">
            <template #body="slotProps">
              <img :src="slotProps.data.icon" :alt="slotProps.data.icon" class="product-image"/>
            </template>
          </Column>
          <Column field="dateAdd" header="dateAdd" sortable></Column>
          <Column field="dateUpdate" header="dateUpdate" sortable></Column>
          <Column field="roles" header="roles" sortable></Column>
          <Column>
            <template #body="slotProps">
              <Button icon="pi pi-pencil" class="p-button-rounded p-button-success p-mr-2"
                      @click="editProduct(slotProps.data)"/>
              <Button icon="pi pi-trash" class="p-button-rounded p-button-warning"
                      @click="confirmDeleteProduct(slotProps.data)"/>
            </template>
          </Column>
        </DataTable>

        <Dialog :visible.sync="productDialog" :style="{width: '450px'}" header="User Details" :modal="true"
                class="p-fluid">
          <img :src="'assets/layout/images/product/' + user.image" :alt="user.image" class="product-image"
               v-if="user.image"/>
          <div class="p-field">
            <label for="userName">userName</label>
            <InputText id="userName" v-model.trim="user.userName" required="true" autofocus
                       :class="{'p-invalid': submitted && !user.userName}"/>
            <small class="p-invalid" v-if="submitted && !user.userName">userName is required.</small>
          </div>
          <div class="p-field">
            <label for="passWord">passWord</label>
            <InputText id="passWord" v-model.trim="user.passWord" required="true" autofocus
                       :class="{'p-invalid': submitted && !user.passWord}"/>
            <small class="p-invalid" v-if="submitted && !user.passWord">passWord is required.</small>
          </div>

          <div class="p-field">
            <label for="icon">icon</label>
            <el-upload
                id="icon"
                class="avatar-uploader"
                :action="url"
                :show-file-list="false"
                :on-success="handleAvatarSuccess">
<!--                :before-upload="beforeAvatarUpload"-->
<!--                >-->
              <img v-if="user.icon" :src="user.icon" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>
          <div class="p-field">
            <label for="roles">roles</label>
            <InputText id="roles" v-model.trim="user.roles" required="true" autofocus
                       :class="{'p-invalid': submitted && !user.roles}"/>
            <small class="p-invalid" v-if="submitted && !user.roles">roles is required.</small>
          </div>

          <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
            <Button label="Save" icon="pi pi-check" class="p-button-text" @click="saveProduct"/>
          </template>
        </Dialog>

        <Dialog :visible.sync="deleteProductDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
          <div class="confirmation-content">
            <i class="pi pi-exclamation-triangle p-mr-3" style="font-size: 2rem"/>
            <span v-if="user">Are you sure you want to delete <b>{{ user.userName }}</b>?</span>
          </div>
          <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductDialog = false"/>
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteProduct"/>
          </template>
        </Dialog>

        <Dialog :visible.sync="deleteProductsDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
          <div class="confirmation-content">
            <i class="pi pi-exclamation-triangle p-mr-3" style="font-size: 2rem"/>
            <span v-if="user">Are you sure you want to delete the selected products?</span>
          </div>
          <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductsDialog = false"/>
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteSelectedProducts"/>
          </template>
        </Dialog>
      </div>
    </div>
  </div>

</template>

<script>
import UserService from '../../service/UserService';
import net from "@/api/baseUrl";
export default {
  data() {
    return {
      url:null,
      users: null,
      productDialog: false,
      deleteProductDialog: false,
      deleteProductsDialog: false,
      user: {},
      selectedUsers: null,
      filters: {},
      submitted: false
    }
  },
  userService: null,
  created() {
    this.userService = new UserService();
  },
  mounted() {
    this.userService.getUsers().then(res => this.users = res.data.data);
    this.url=net.BASE_URL+'/file/upload';
  },
  methods: {
    handleAvatarSuccess(response) {
      // this.imageUrl = URL.createObjectURL(file.raw);
      this.user.icon=response.data;
    },
    beforeAvatarUpload(file) {
      // const isJPG = file.type === 'image/jpeg';
      // const isPNG = file.type === 'image/png';
      // const isLt2M = file.size / 1024 / 1024 < 2;
      //
      // if (!isJPG && !isPNG) {
      //   this.$message.error('上传头像图片只能是 JPG或者PNG 格式!');
      // }
      // if (!isLt2M) {
      //   this.$message.error('上传头像图片大小不能超过 2MB!');
      // }
      return true;
    },
    openNew() {
      this.user = {};
      this.submitted = false;
      this.productDialog = true;
    },
    hideDialog() {
      this.productDialog = false;
      this.submitted = false;
    },
    saveProduct() {
      this.submitted = true;
      this.userService.saveUser(this.user);
      this.$toast.add({severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000});
      this.productDialog = false;
      this.user = {};
      this.$router.go(0)
    },
    editProduct(product) {
      this.user = {...product};
      this.productDialog = true;
    },
    confirmDeleteProduct(product) {
      this.user = product;
      this.deleteProductDialog = true;
    },
    deleteProduct() {
      this.users = this.users.filter(val => val.id !== this.user.id);
      this.userService.deleteUser(this.user);
      this.deleteProductDialog = false;
      this.user = {};
      this.$toast.add({severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000});
    },
    findIndexById(id) {
      let index = -1;
      for (let i = 0; i < this.users.length; i++) {
        if (this.users[i].id === id) {
          index = i;
          break;
        }
      }
      return index;
    },
    createId() {
      let id = '';
      var chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      for ( var i = 0; i < 5; i++ ) {
        id += chars.charAt(Math.floor(Math.random() * chars.length));
      }
      return id;
    },
    exportCSV() {
      this.$refs.dt.exportCSV();
    },
    confirmDeleteSelected() {
      this.deleteProductsDialog = true;
    },
    deleteSelectedProducts() {
      this.users = this.users.filter(val => !this.selectedUsers.includes(val));
      this.userService.deleteUserBatch(this.selectedUsers);
      this.deleteProductsDialog = false;
      this.selectedUsers = null;
      this.$toast.add({severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000});
    },
    onUpload() {
      this.$toast.add({severity: 'info', summary: 'Success', detail: 'File Uploaded', life: 3000});
    },

  }
}
</script>

<style scoped lang="scss">
.table-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.product-image {
  width: 100px;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
}

.p-dialog .product-image {
  width: 150px;
  margin: 0 auto 2rem auto;
  display: block;
}

.confirmation-content {
  display: flex;
  align-items: center;
  justify-content: center;
}

.product-badge {
  border-radius: 2px;
  padding: .25em .5rem;
  text-transform: uppercase;
  font-weight: 700;
  font-size: 12px;
  letter-spacing: .3px;

  &.status-instock {
    background: #C8E6C9;
    color: #256029;
  }

  &.status-outofstock {
    background: #FFCDD2;
    color: #C63737;
  }

  &.status-lowstock {
    background: #FEEDAF;
    color: #8A5340;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
}
</style>
