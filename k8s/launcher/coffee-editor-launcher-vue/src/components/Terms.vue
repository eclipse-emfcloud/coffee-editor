<template>
  <div class="disclaimer">
    <v-expansion-panels dark flat>
      <v-expansion-panel>
        <v-expansion-panel-header expand-icon="expand">
          <h1>Coffee Editor Terms and Conditions, Privacy Policy</h1>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <h3>1. Terms</h3>
          <p>
            The provider of this service is EclipseSource GmbH, registered
            office Messerschmittstrasse 4, 80992 Muenchen (Commercial Register
            Muenchen, HRB 191789), represented by general managers Dr. Jonas
            Helming, and Dr. Maximilian Koegel (addressed as we/our, Coffee
            Editor).
          </p>
          <p>
            By accessing the online Coffee Editor, you are agreeing to be bound
            by these web site Terms and Conditions of Use, all applicable laws
            and regulations, and agree that you are responsible for compliance
            with any applicable local laws. If you do not agree with any of
            these terms, you are prohibited from using or accessing this site.
          </p>
          <h3>2. Use License</h3>
          <ul>
            <li>
              Permission is granted to use the Coffee Editor in order to explore
              the features of this online modeling application.
            </li>
            <li>
              The IDE will be brought down after 30 minutes of use. Any data
              that was not downloaded before that time will be lost. We do not
              provide any warranty for data loss or defects caused by downloaded
              material.
            </li>
          </ul>
          <h3>3. Disclaimer</h3>
          <ul>
            <li>
              The materials on this web site are provided "as is". Coffee Editor
              makes no warranties, expressed or implied, and hereby disclaims
              and negates all other warranties, including without limitation,
              implied warranties or conditions of merchantability, fitness for a
              particular purpose, or non-infringement of intellectual property
              or other violation of rights. Further, Coffee Editor does not
              warrant or make any representations concerning the accuracy,
              likely results, or reliability of the use of the materials on its
              Internet web site or otherwise relating to such materials or on
              any sites linked to this site.
            </li>
          </ul>
          <h3>4. Limitations</h3>
          <p>
            In no event shall Coffee Editor or its suppliers be liable for any
            damages (including, without limitation, damages for loss of data or
            profit, or due to business interruption,) arising out of the use or
            inability to use the materials on Coffee Editor's Internet site,
            even if Coffee Editor or a Coffee Editor authorized representative
            has been notified orally or in writing of the possibility of such
            damage. Because some jurisdictions do not allow limitations on
            implied warranties, or limitations of liability for consequential or
            incidental damages, these limitations may not apply to you.
          </p>
          <h3>5. Revisions and Errata</h3>
          <p>
            The materials appearing on Coffee Editor's web site could include
            technical, typographical, or photographic errors. Coffee Editor does
            not warrant that any of the materials on its web site are accurate,
            complete, or current. Coffee Editor may make changes to the
            materials contained on its web site at any time without notice.
            Coffee Editor does not, however, make any commitment to update the
            materials.
          </p>
          <h3>6. Site Terms of Use Modifications</h3>
          <p>
            Coffee Editor may revise these terms of use for its web site at any
            time without notice. By using this web site you are agreeing to be
            bound by the then current version of these Terms and Conditions of
            Use.
          </p>
          <p>General Terms and Conditions applicable to Use of a Web Site.</p>
          <h2>Privacy Policy</h2>
          <p>
            We do not guarantee the privacy of data edited or created while
            using the online Coffee Editor. This is a demo application and
            should be treated as such.
          </p>
          <p>
            We don't collect personal data, but we may use third-party
            applications that might do so (e.g. Google Analytics, Social
            Networks). We might add/remove third party plugins at any time
            without notice. By launching the Coffee Editor you agree that the
            editor may set cookies.
          </p>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Terms extends Vue {}
</script>

<style scoped>
.disclaimer {
  padding: 120px;
}

ul {
  list-style-type: none;
}

.theme--dark.v-expansion-panels .v-expansion-panel {
  background-color: #313131;
  color: #e2e2e2;
}
</style>
