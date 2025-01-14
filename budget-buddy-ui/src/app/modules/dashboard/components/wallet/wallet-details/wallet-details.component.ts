import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { WalletResponse } from '../../../../../services/models/wallet-response';
import { FindWalletById$Params } from '../../../../../services/fn/wallet/find-wallet-by-id';
import { WalletService } from '../../../../../services/services/wallet.service';
import {animate, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-wallet-details',
  templateUrl: './wallet-details.component.html',
  styleUrls: ['./wallet-details.component.css'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('300ms', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('300ms', style({ opacity: 0 }))
      ])
    ]),
    trigger('scaleInOut', [
      transition(':enter', [
        style({ transform: 'scale(0)' }),
        animate('300ms', style({ transform: 'scale(1)' }))
      ]),
      transition(':leave', [
        animate('300ms', style({ transform: 'scale(0)' }))
      ])
    ])
  ]
})
export class WalletDetailsComponent implements OnInit{
  @Input() walletId: number | undefined;
  @Output() onClose = new EventEmitter<void>();
  walletResponseDetails: WalletResponse = {};

  constructor(
    private walletService: WalletService,
  ) {}

  ngOnInit() {
    this.toggleDetails(this.walletId);
  }

  toggleDetails(walletId: number | undefined) {

    if (!walletId) {
      console.error('No wallet ID provided');
      return;
    }
    const params: FindWalletById$Params = { 'wallet-id': walletId };
    this.walletService.findWalletById(params).subscribe({
      next: (wallet) => {
        this.walletResponseDetails = wallet;
      },
      error: (err) => {
        console.error('Error fetching wallet details:', err);
      }
    });
  }

  // Method to close the details view and emit onClose event
  closeDetails() {
    this.onClose.emit(); // Emit the close event to notify the parent component
  }
}
