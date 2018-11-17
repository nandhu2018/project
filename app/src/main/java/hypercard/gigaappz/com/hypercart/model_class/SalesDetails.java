package hypercard.gigaappz.com.hypercart.model_class;

/**
 * Created by DELL on 06-Nov-18.
 */

public class SalesDetails {
    private String billNumber;
    private String totalCost;
    private String billDate;
    private String mobile;
    public SalesDetails() {
    }

    public SalesDetails(String billNumber, String totalCost, String billDate,String mobile) {
        this.billNumber = billNumber;
        this.totalCost = totalCost;
        this.billDate = billDate;
        this.mobile=mobile;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
