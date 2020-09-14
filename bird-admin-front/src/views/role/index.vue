<template>
  <div class="p-grid crud-demo">
    <div class="p-col-12">
      <div class="card">
        <Toast/>
        <Toolbar class="p-mb-4">
          <template slot="left">
            <Button label="New" icon="pi pi-plus" class="p-button-success p-mr-2" @click="openNew" />
            <Button label="Delete" icon="pi pi-trash" class="p-button-danger" @click="confirmDeleteSelected" :disabled="!selectedProducts || !selectedProducts.length" />
          </template>

          <template slot="right">
            <FileUpload mode="basic" accept="image/*" :maxFileSize="1000000" label="Import" chooseLabel="Import" class="p-mr-2 p-d-inline-block" />
            <Button label="Export" icon="pi pi-upload" class="p-button-help" @click="exportCSV($event)"  />
          </template>
        </Toolbar>

        <DataTable ref="dt" :value="roles" :selection.sync="selectedProducts" dataKey="id" :paginator="true" :rows="10" :filters="filters"
                   paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
                   currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products">
          <template #header>
            <div class="table-header">
              <h5 class="p-m-0">Manage Roles</h5>
              <span class="p-input-icon-left">
                                <i class="pi pi-search" />
                                <InputText v-model="filters['global']" placeholder="Search..." />
                            </span>
            </div>
          </template>

          <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
          <Column field="id" header="id" sortable></Column>
          <Column field="name" header="name" sortable></Column>
          <Column field="descption" header="descption" sortable></Column>
          <Column field="dateAdd" header="dateAdd" sortable></Column>
          <Column field="dateUpdate" header="dateUpdate" sortable></Column>
          <Column field="permissions" header="permissions" sortable></Column>
          <Column>
            <template #body="slotProps">
              <Button icon="pi pi-pencil" class="p-button-rounded p-button-success p-mr-2" @click="editRole(slotProps.data)" />
              <Button icon="pi pi-trash" class="p-button-rounded p-button-warning" @click="confirmDeleteProduct(slotProps.data)" />
            </template>
          </Column>
        </DataTable>

        <Dialog :visible.sync="productDialog" :style="{width: '450px'}" header="Product Details" :modal="true" class="p-fluid">
          <img :src="'assets/layout/images/product/' + role.image" :alt="role.image" class="product-image" v-if="role.image" />
          <div class="p-field">
            <label for="name">Name</label>
            <InputText id="name" v-model.trim="role.name" required="true" autofocus :class="{'p-invalid': submitted && !role.name}" />
            <small class="p-invalid" v-if="submitted && !role.name">Name is required.</small>
          </div>
          <div class="p-field">
            <label for="descption">Description</label>
            <Textarea id="descption" v-model="role.descption" required="true" rows="3" cols="20" />
          </div>

          <div class="p-formgrid p-grid">
            <div class="p-field p-col">
              <label for="permissions">permissions</label>
              <Textarea id="permissions" v-model="role.permissions" required="true" rows="3" cols="20" />
            </div>
          </div>
          <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
            <Button label="Save" icon="pi pi-check" class="p-button-text" @click="saveProduct" />
          </template>
        </Dialog>

        <Dialog :visible.sync="deleteProductDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
          <div class="confirmation-content">
            <i class="pi pi-exclamation-triangle p-mr-3" style="font-size: 2rem" />
            <span v-if="role">Are you sure you want to delete <b>{{ role.name }}</b>?</span>
          </div>
          <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductDialog = false"/>
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteProduct" />
          </template>
        </Dialog>

        <Dialog :visible.sync="deleteProductsDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
          <div class="confirmation-content">
            <i class="pi pi-exclamation-triangle p-mr-3" style="font-size: 2rem" />
            <span v-if="role">Are you sure you want to delete the selected products?</span>
          </div>
          <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductsDialog = false"/>
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteSelectedProducts" />
          </template>
        </Dialog>
      </div>
    </div>
  </div>

</template>

<script>
import RoleService from "@/service/RoleService";

export default {
  data() {
    return {
      roles: null,
      productDialog: false,
      deleteProductDialog: false,
      deleteProductsDialog: false,
      role: {},
      selectedProducts: null,
      filters: {},
      submitted: false
    }
  },
  roleService: null,
  created() {
    this.roleService = new RoleService();
  },
  mounted() {
    this.roleService.getRoles().then(res => this.roles = res.data.data);
  },
  methods: {
    openNew() {
      this.role = {};
      this.submitted = false;
      this.productDialog = true;
    },
    hideDialog() {
      this.productDialog = false;
      this.submitted = false;
    },
    saveProduct() {
      this.submitted = true;
      this.roleService.saveRole(this.role)
      this.$toast.add({severity:'success', summary: 'Successful', detail: 'Product Created', life: 3000});
      this.productDialog = false;
      this.role = {};
      this.$router.go(0)
    },
    editRole(role) {
      this.role = {...role};
      this.productDialog = true;
    },
    confirmDeleteProduct(product) {
      this.role = product;
      this.deleteProductDialog = true;
    },
    deleteProduct() {
      this.roles = this.roles.filter(val => val.id !== this.role.id);
      this.roleService.deleteRole(this.role)
      this.deleteProductDialog = false;
      this.role = {};
      this.$toast.add({severity:'success', summary: 'Successful', detail: 'Product Deleted', life: 3000});
    },
    findIndexById(id) {
      let index = -1;
      for (let i = 0; i < this.roles.length; i++) {
        if (this.roles[i].id === id) {
          index = i;
          break;
        }
      }

      return index;
    },
    exportCSV() {
      this.$refs.dt.exportCSV();
    },
    confirmDeleteSelected() {
      this.deleteProductsDialog = true;
    },
    deleteSelectedProducts() {
      this.roles = this.roles.filter(val => !this.selectedProducts.includes(val));
      this.roleService.deleteRoleBatch(this.selectedProducts);
      this.deleteProductsDialog = false;
      this.selectedProducts = null;
      this.$toast.add({severity:'success', summary: 'Successful', detail: 'Products Deleted', life: 3000});
    }
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
}
</style>
