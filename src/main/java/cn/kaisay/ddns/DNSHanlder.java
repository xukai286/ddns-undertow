package cn.kaisay.ddns;

import com.azure.core.credential.TokenCredential;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.DefaultAzureCredentialBuilder;
// import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.resourcemanager.dns.DnsZoneManager;



//see more from https://github.com/Azure/azure-sdk-for-java/tree/main/sdk/identity/azure-identity
//https://github.com/Azure/azure-sdk-for-java/wiki/Azure-Identity-Examples#authenticating-with-defaultazurecredential
//ENF_8NUk1JA~PV.Xcoc8Lkf3FZjfc.9P7L
//client id ad9ce3e4-dead-49c5-99e9-3b4c2a85a12c
//Tenant id 3f48b5b2-2a99-4503-b2b3-c89c6ef2aab4
public class DNSHanlder {

    public void authenticate(String ip) {
        // BEGIN: readme-sample-authenticate
        AzureProfile profile = new AzureProfile(AzureEnvironment.AZURE_CHINA);
        TokenCredential credential = new DefaultAzureCredentialBuilder()
                .authorityHost(profile.getEnvironment().getActiveDirectoryEndpoint())
                .build();
        DnsZoneManager manager = DnsZoneManager
                .authenticate(credential, profile);
        manager.zones()
                .define("kaisay.cn").withExistingResourceGroup("openworld").defineARecordSet("nas")
                .withIPv4Address(ip).withTimeToLive(3600).attach().create();

        // END: readme-sample-authenticate
    }
}
