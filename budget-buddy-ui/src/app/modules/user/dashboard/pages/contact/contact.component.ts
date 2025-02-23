import {Component, OnInit} from '@angular/core';
import {PageResponseBudgetResponse} from '../../../../../services/models/page-response-budget-response';
import {BudgetResponse} from '../../../../../services/models/budget-response';
import {ContactRequest} from '../../../../../services/models/contact-request';
import {ContactService} from '../../../../../services/services/contact.service';
import {CreateContact$Params} from '../../../../../services/fn/contact/create-contact';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {
  errorMsg: Array<string> = [];
  level: 'success' | 'error' = 'success';
  showSuccess = false;
  contactRequest: ContactRequest = {
    email: '',
    subject: '',
    message: ''
  };

  constructor(private contactService: ContactService,
              private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit() {
    const navigation = this.activatedRoute.snapshot.queryParams;
    this.contactRequest.email = history.state?.email || navigation['email'] || '';
  }


  sendMessage() {
    const params: CreateContact$Params = {
      body: this.contactRequest
    }
    this.contactService.createContact(params).subscribe({
      next: () => {
        this.showSuccess = true;
      },
      error: (err) => {
        console.error('Error sending message:', err);
        this.errorMsg.push(err.error.error);
        this.level = 'error';
      }
    });
  }


  closeError() {
    this.errorMsg = [];
  }

  closePopup() {
    this.contactRequest= {
      subject: '',
      message: ''
    }
    this.showSuccess = false;
  }
}
