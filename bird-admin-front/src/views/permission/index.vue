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

        <DataTable ref="dt" :value="permissions" :selection.sync="selectedProducts" dataKey="id" :paginator="true" :rows="10" :filters="filters"
                   paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
                   currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products">
          <template #header>
            <div class="table-header">
              <h5 class="p-m-0">Manage Permission</h5>
              <span class="p-input-icon-left">
                                <i class="pi pi-search" />
                                <InputText v-model="filters['global']" placeholder="Search..." />
                            </span>
            </div>
          </template>

          <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
          <Column field="id" header="id" sortable></Column>
          <Column field="role_id" header="role_id" sortable></Column>
          <Column field="permission" header="permission" sortable></Column>
          <Column field="add_time" header="add_time" sortable></Column>
          <Column field="update_time" header="update_time" sortable></Column>
          <Column field="deleted" header="deleted" sortable></Column>
          <!--          <Column field="rating" header="Reviews" sortable>-->
          <!--            <template #body="slotProps">-->
          <!--              <Rating :value="slotProps.data.rating" :readonly="true" :cancel="false" />-->
          <!--            </template>-->
          <!--          </Column>-->
          <!--          <Column field="inventoryStatus" header="Status" sortable>-->
          <!--            <template #body="slotProps">-->
          <!--              <span :class="'product-badge status-' + slotProps.data.inventoryStatus.toLowerCase()">{{slotProps.data.inventoryStatus}}</span>-->
          <!--            </template>-->
          <!--          </Column>-->
          <Column>
            <template #body="slotProps">
              <Button icon="pi pi-pencil" class="p-button-rounded p-button-success p-mr-2" @click="editProduct(slotProps.data)" />
              <Button icon="pi pi-trash" class="p-button-rounded p-button-warning" @click="confirmDeleteProduct(slotProps.data)" />
            </template>
          </Column>
        </DataTable>

        <Dialog :visible.sync="productDialog" :style="{width: '450px'}" header="Product Details" :modal="true" class="p-fluid">
          <img :src="'assets/layout/images/product/' + permission.image" :alt="permission.image" class="product-image" v-if="permission.image" />
          <div class="p-field">
            <label for="name">Name</label>
            <InputText id="name" v-model.trim="permission.username" required="true" autofocus :class="{'p-invalid': submitted && !permission.username}" />
            <small class="p-invalid" v-if="submitted && !permission.username">Name is required.</small>
          </div>
          <div class="p-field">
            <label for="description">Description</label>
            <Textarea id="description" v-model="permission.description" required="true" rows="3" cols="20" />
          </div>

          <div class="p-field">
            <label class="p-mb-3">Category</label>
            <div class="p-formgrid p-grid">
              <div class="p-field-radiobutton p-col-6">
                <RadioButton id="category1" name="category" value="Accessories" v-model="permission.category" />
                <label for="category1">Accessories</label>
              </div>
              <div class="p-field-radiobutton p-col-6">
                <RadioButton id="category2" name="category" value="Clothing" v-model="permission.category" />
                <label for="category2">Clothing</label>
              </div>
              <div class="p-field-radiobutton p-col-6">
                <RadioButton id="category3" name="category" value="Electronics" v-model="permission.category" />
                <label for="category3">Electronics</label>
              </div>
              <div class="p-field-radiobutton p-col-6">
                <RadioButton id="category4" name="category" value="Fitness" v-model="permission.category" />
                <label for="category4">Fitness</label>
              </div>
            </div>
          </div>

          <div class="p-formgrid p-grid">
            <div class="p-field p-col">
              <label for="price">Price</label>
              <InputNumber id="price" v-model="permission.price" mode="currency" currency="USD" locale="en-US" />
            </div>
            <div class="p-field p-col">
              <label for="quantity">Quantity</label>
              <InputNumber id="quantity" v-model="permission.quantity" integeronly />
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
            <span v-if="permission">Are you sure you want to delete <b>{{ permission.name }}</b>?</span>
          </div>
          <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductDialog = false"/>
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteProduct" />
          </template>
        </Dialog>

        <Dialog :visible.sync="deleteProductsDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
          <div class="confirmation-content">
            <i class="pi pi-exclamation-triangle p-mr-3" style="font-size: 2rem" />
            <span v-if="permission">Are you sure you want to delete the selected products?</span>
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
import PermissionService from "../../service/PermissionService";

export default {
  data() {
    return {
      permissions: null,
      productDialog: false,
      deleteProductDialog: false,
      deleteProductsDialog: false,
      permission: {},
      selectedProducts: null,
      filters: {},
      submitted: false
    }
  },
  permissionService: null,
  created() {
    this.permissionService = new PermissionService();
  },
  mounted() {
    this.permissionService.getPermissions().then(data => this.permissions = data);
  },
  methods: {
    openNew() {
      this.permission = {};
      this.submitted = false;
      this.productDialog = true;
    },
    hideDialog() {
      this.productDialog = false;
      this.submitted = false;
    },
    saveProduct() {
      this.submitted = true;

      if (this.permission.name.trim()) {
        if (this.permission.id) {
          this.$set(this.permissions, this.findIndexById(this.permission.id), this.permission);
          this.$toast.add({severity:'success', summary: 'Successful', detail: 'Product Updated', life: 3000});
        }
        else {
          this.permission.id = this.createId();
          this.permission.code = this.createId();
          this.permission.image = 'product-placeholder.svg';
          this.permission.inventoryStatus = 'INSTOCK';
          this.permissions.push(this.permission);
          this.$toast.add({severity:'success', summary: 'Successful', detail: 'Product Created', life: 3000});
        }

        this.productDialog = false;
        this.permission = {};
      }
    },
    editProduct(product) {
      this.permission = {...product};
      this.productDialog = true;
    },
    confirmDeleteProduct(product) {
      this.permission = product;
      this.deleteProductDialog = true;
    },
    deleteProduct() {
      this.permissions = this.permissions.filter(val => val.id !== this.permission.id);
      this.deleteProductDialog = false;
      this.permission = {};
      this.$toast.add({severity:'success', summary: 'Successful', detail: 'Product Deleted', life: 3000});
    },
    findIndexById(id) {
      let index = -1;
      for (let i = 0; i < this.permissions.length; i++) {
        if (this.permissions[i].id === id) {
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
      this.permissions = this.permissions.filter(val => !this.selectedProducts.includes(val));
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
