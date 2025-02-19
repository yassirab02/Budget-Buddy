import { Component } from '@angular/core';
import {ContactRequest} from '../../services/models/contact-request';
import {ContactService} from '../../services/services/contact.service';
import {ActivatedRoute} from '@angular/router';
import {CreateContact$Params} from '../../services/fn/contact/create-contact';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {
  errorMsg: Array<string> = [];
  level: 'success' | 'error' = 'success';
  showSuccess = false;
  contactRequest: ContactRequest = {
    email: '',
    subject: '',
    message: ''
  };

  constructor(private contactService: ContactService,
  ) {
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
    this.contactRequest = {
      subject: '',
      message: ''
    };
    this.showSuccess = false;
  }


}
