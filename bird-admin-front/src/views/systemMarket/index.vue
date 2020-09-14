<template>
  <div class="p-grid p-fluid dashboard">
    <div class="p-col-12 p-lg-3">
      <div class="card summary">
        <span class="detail" style="align-content: center;text-align: center;display:block">Uptime</span>
        <span class="title" style="align-content: center;text-align: center;display:block;color: red">
          {{ this.jvmInfo.upTime }}
        </span>
      </div>
    </div>
    <div class="p-col-12 p-lg-3">
      <div class="card summary">
        <span class="detail" style="align-content: center;text-align: center;display:block">Start time</span>
        <span class="title" style="align-content: center;text-align: center;display:block;color: red">
          {{ this.jvmInfo.startTime }}
        </span>
      </div>
    </div>
    <div class="p-col-12 p-lg-3">
      <div class="card summary">
        <span class="detail" style="align-content: center;text-align: center;display:block">Heap used</span>
        <span class="title" style="align-content: center;text-align: center;display:block;color: green">
            {{ this.jvmInfo.heapUsed }}
        </span>
      </div>
    </div>
    <div class="p-col-12 p-lg-3">
      <div class="card summary">
        <span class="detail" style="align-content: center;text-align: center;display:block">SystemLoadAverage</span>
        <span class="title" style="align-content: center;text-align: center;display:block;color: green">
        {{ this.jvmInfo.systemLoadAverage }}
        </span>
      </div>
    </div>


    <div class="card" style="width: 100%">
      <h1 style="font-size:16px">JVM Memory</h1>
      <Chart type="line" :data="this.jvmInfo.jvmMemoryChart"/>
    </div>

    <div class="card" style="width: 100%">
      <h1 style="font-size:16px">JVM Thread</h1>
      <Chart type="line" :data="this.jvmInfo.jvmThreadChart"/>
    </div>

    <div class="card" style="width: 100%">
      <h1 style="font-size:16px">Garbage Collection Time</h1>
      <Chart type="line" :data="this.jvmInfo.jvmGCChart"/>
    </div>

        <div class="card" style="width: 100%">
          <h1 style="font-size:16px">Disk</h1>
          <Chart type="line" :data="this.jvmInfo.diskChart"/>
        </div>

  </div>

</template>

<script>
import JvmInfoService from "@/service/JvmInfoService";

export default {
  data() {
    return {
      jvmInfo: null,
    }
  },
  jvmInfoService: null,
  created() {
    this.jvmInfoService = new JvmInfoService();
  },
  mounted() {
    this.jvmInfoService.getJvm().then(res => this.jvmInfo = res.data.data);
    setInterval(this.get, 1000*60)
  },
  methods: {
    get() {
      this.jvmInfoService.getJvm().then(res => this.jvmInfo = res.data.data);
    }
  }
}
</script>

<style lang="scss" scoped>
@media screen and (max-width: 960px) {
  /deep/ .p-datatable {
    &.p-datatable-customers {
      .p-datatable-thead > tr > th,
      .p-datatable-tfoot > tr > td {
        display: none !important;
      }

      .p-datatable-tbody > tr {
        border-bottom: 1px solid #dee2e6;

        > td {
          text-align: left;
          display: flex;
          align-items: center;
          justify-content: center;
          border: 0 none !important;
          width: 100% !important;
          float: left;
          clear: left;
          border: 0 none;

          .p-column-title {
            padding: .4rem;
            min-width: 30%;
            display: inline-block;
            margin: -.4rem 1rem -.4rem -.4rem;
            font-weight: bold;
          }

          .p-progressbar {
            margin-top: .5rem;
          }
        }
      }
    }
  }
}
</style>
