package com.voidstudio.quickcashreg.management;

import com.voidstudio.quickcashreg.jobpost.Job;

import users.Employer;

public class EmployerContractManager implements IContractManager{



  private Employer employer;

  public EmployerContractManager(Employer e){
    this.employer = e;
  }


  @Override
  public void acceptContract(Job j) {
    //TODO: Next Iteration, the employer will be able to accept job contracts.
  }

  @Override
  public void setContractStatus(JobContract jc, String status) {
    if(status.equals(ManagementConstants.PAY)){
      IJobActions action = new Pay();
      JobContract paidContract = action.execute(jc);
      completedContracts.remove(paidContract);
    }
  }
}
