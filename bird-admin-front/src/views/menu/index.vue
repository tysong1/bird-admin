<template xmlns="http://www.w3.org/1999/html">
  <div class="p-grid crud-demo">
    <div class="p-col-12">
      <div class="card">
        <Toast/>
        <Toolbar class="p-mb-4">
          <template slot="left">
            <Button label="New" icon="pi pi-plus" class="p-button-success p-mr-2" @click="openNew"/>
            <Button label="Delete" icon="pi pi-trash" class="p-button-danger" @click="confirmDeleteSelected"
                    :disabled="!selectedMenus || !selectedMenus.length"/>
          </template>

          <template slot="right">
            <FileUpload mode="basic" accept="image/*" :maxFileSize="1000000" label="Import" chooseLabel="Import"
                        class="p-mr-2 p-d-inline-block"/>
            <Button label="Export" icon="pi pi-upload" class="p-button-help" @click="exportCSV($event)"/>
          </template>
        </Toolbar>

        <DataTable ref="dt" :value="menus" :selection.sync="selectedMenus" dataKey="id" :paginator="true" :rows="10"
                   :filters="filters"
                   paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                   :rowsPerPageOptions="[5,10,25]"
                   currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products">
          <template #header>
            <div class="table-header">
              <h5 class="p-m-0">Manage Menus</h5>
              <span class="p-input-icon-left">
                                <i class="pi pi-search"/>
                                <InputText v-model="filters['global']" placeholder="Search..."/>
                            </span>
            </div>
          </template>

          <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
          <Column field="id" header="id" sortable></Column>
          <Column field="label" header="label" sortable></Column>
          <Column field="toJump" header="toJump" sortable></Column>

          <Column field="permission" header="permission" sortable></Column>
          <Column field="role" header="role" sortable></Column>
          <Column field="parentId" header="parentId" sortable></Column>
          <Column>
            <template #body="slotProps">
              <Button icon="pi pi-pencil" class="p-button-rounded p-button-success p-mr-2"
                      @click="editMenu(slotProps.data)"/>
              <Button icon="pi pi-trash" class="p-button-rounded p-button-warning"
                      @click="confirmDeleteMenu(slotProps.data)"/>
            </template>
          </Column>
        </DataTable>

        <Dialog :visible.sync="productDialog" :style="{width: '450px'}" header="User Details" :modal="true"
                class="p-fluid">
          <img :src="'assets/layout/images/product/' + menu.image" :alt="menu.image" class="product-image"
               v-if="menu.image"/>
          <div class="p-field">
            <label for="label">label</label>
            <InputText id="label" v-model.trim="menu.label" required="true" autofocus
                       :class="{'p-invalid': submitted && !menu.label}"/>
            <small class="p-invalid" v-if="submitted && !menu.label">label is required.</small>
          </div>
          <div class="p-field">
            <label for="toJump">toJump</label>
            <InputText id="toJump" v-model.trim="menu.toJump" autofocus
                       :class="{'p-invalid': submitted }"/>
            <small class="p-invalid" v-if="submitted && !menu.toJump">toJump is required.</small>
          </div>

          <div class="p-field">
            <label for="permission">permission</label>
            <InputText id="permission" v-model.trim="menu.permission" autofocus
                       :class="{'p-invalid': submitted && !menu.permission}"/>
            <small class="p-invalid" v-if="submitted && !menu.permission">permission is required.</small>
          </div>

          <div class="p-field">
            <label for="role">role</label>
            <InputText id="role" v-model.trim="menu.role"  autofocus
                       :class="{'p-invalid': submitted && !menu.role}"/>
            <small class="p-invalid" v-if="submitted && !menu.role">role is required.</small>
          </div>

          <div class="p-field">
            <label for="parentId">parentId</label>
            <InputText id="parentId" v-model.trim="menu.parentId"  autofocus
                       :class="{'p-invalid': submitted && !menu.parentId}"/>
            <small class="p-invalid" v-if="submitted && !menu.parentId">parentId is required.</small>
          </div>

          <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
            <Button label="Save" icon="pi pi-check" class="p-button-text" @click="saveMenu"/>
          </template>
        </Dialog>

        <Dialog :visible.sync="deleteProductDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
          <div class="confirmation-content">
            <i class="pi pi-exclamation-triangle p-mr-3" style="font-size: 2rem"/>
            <span v-if="menu">Are you sure you want to delete <b>{{ menu.userName }}</b>?</span>
          </div>
          <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductDialog = false"/>
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteMenu"/>
          </template>
        </Dialog>

        <Dialog :visible.sync="deleteProductsDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
          <div class="confirmation-content">
            <i class="pi pi-exclamation-triangle p-mr-3" style="font-size: 2rem"/>
            <span v-if="menu">Are you sure you want to delete the selected products?</span>
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
import MenuService from "@/service/MenuService";
export default {
  data() {
    return {
      menus: null,
      productDialog: false,
      deleteProductDialog: false,
      deleteProductsDialog: false,
      menu: {},
      selectedMenus: null,
      filters: {},
      submitted: false
    }
  },
  menuService: null,
  created() {
    this.menuService = new MenuService();
  },
  mounted() {
    this.menuService.getMenus().then(res => this.menus = res.data.data);
  },
  methods: {
    handleAvatarSuccess(response) {
      // this.imageUrl = URL.createObjectURL(file.raw);
      this.menu.icon=response.data[0];
    },

    openNew() {
      this.menu = {};
      this.submitted = false;
      this.productDialog = true;
    },
    hideDialog() {
      this.productDialog = false;
      this.submitted = false;
    },
    saveMenu() {
      this.submitted = true;
      this.menuService.saveMenu(this.menu);
      this.$toast.add({severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000});
      this.productDialog = false;
      this.menu = {};
      this.$router.go(0)
    },
    editMenu(product) {
      this.menu = {...product};
      this.productDialog = true;
    },
    confirmDeleteMenu(product) {
      this.menu = product;
      this.deleteProductDialog = true;
    },
    deleteMenu() {
      this.menus = this.menus.filter(val => val.id !== this.menu.id);
      this.menuService.deleteMenu(this.menu);
      this.deleteProductDialog = false;
      this.menu = {};
      this.$toast.add({severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000});
    },
    findIndexById(id) {
      let index = -1;
      for (let i = 0; i < this.menus.length; i++) {
        if (this.menus[i].id === id) {
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
      this.menus = this.menus.filter(val => !this.selectedMenus.includes(val));
      this.menuService.deleteMenuBatch(this.selectedMenus);
      this.deleteProductsDialog = false;
      this.selectedMenus = null;
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
