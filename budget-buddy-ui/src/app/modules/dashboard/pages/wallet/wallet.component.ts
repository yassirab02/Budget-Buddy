import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {WalletRequest} from '../../../../services/models/wallet-request';
import {WalletService} from '../../../../services/services/wallet.service';
import {PageResponseWalletResponse} from '../../../../services/models/page-response-wallet-response';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrl: './wallet.component.css'
})
export class WalletComponent implements OnInit{
  isLoading: boolean = true;
  page = 0;
  size = 5;
  pages: any = [];
  message = '';
  level: 'success' | 'error' = 'success';
  createWallet = false;
  showSuccess=false
  errorMsg: Array<string> = [];
  walletForm!: FormGroup;
  showDetailsMap: { [key: number]: boolean } = {};
  walletResponse: PageResponseWalletResponse = {};  // Store the actual wallet
  walletRequest: WalletRequest = {
    name: '',
    balance: 0,
    walletType: 'SPENDING'  // Default wallet type
  };

  selectedWalletId: number | null = null;

  constructor(private fb: FormBuilder,
              private walletService: WalletService,
  ) {
  }

  ngOnInit(): void {
    this.walletForm = this.fb.group({
      name: ['', [Validators.required]],
      balance: ['', [Validators.required, Validators.min(0)]],
      walletType: ['SPENDING', Validators.required],  // Set default value here
    });
    this.findAllWallets();
  }

  // Check if details for a specific wallet are visible
  isDetailsVisible(walletId: number | undefined): boolean {
    if (walletId === undefined) {
      console.error('Wallet ID is undefined');
      return false;
    }
    return this.showDetailsMap[walletId] || false;
  }

  toggleDetails(walletId: number| undefined): void {
    if (walletId === undefined) {
      console.error('Wallet ID is undefined');
      return;
    }
    this.selectedWalletId = this.selectedWalletId === walletId ? null : walletId;
  }

  closeWalletDetails() {
    this.selectedWalletId = null;
  }

  findAllWallets(resetPage:boolean = false) {
    if (resetPage) {
      this.page = 0; // Reset to the first page
    }
    this.walletService.findAllWalletsByOwner({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (wallets) => {
          // Store the backend response in budgetResponse
          this.walletResponse = wallets;
          // Create an array of page numbers for pagination
          this.pages = Array(this.walletResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching wallets:', err);
          this.message = 'An error occurred while fetching the wallets.';
          this.level = 'error';
          this.isLoading = false;
        }
      });
  }

  saveWallet() {
    this.walletRequest.walletType = this.walletForm.value.walletType;
    this.walletService.addOrUpdateWallet({
      body: this.walletRequest
    }).subscribe({
      next: (budgetId) => {
        // Set isAdd to false after successful save
        this.walletForm.reset();
        this.showSuccess = true;
        setTimeout(() => {
          this.showSuccess = false;
        }, 5000);
        this.createWallet = false;

        // Refresh the wallet list after successful save
        this.findAllWallets();
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
      }
    });
  }

  editWallet(walletId: number | undefined): void {
    // Logic for editing a wallet
    console.log('Edit wallet with ID:', walletId);
    // Navigate to edit page or open a modal
  }

  deleteWallet(walletId: number | undefined): void {
    // Logic for deleting a wallet
    if (confirm('Are you sure you want to delete this wallet?')) {
      console.log('Delete wallet with ID:', walletId);
      // Call your service to delete the wallet
    }
  }

  toggleDialog() {
    this.createWallet = !this.createWallet;
  }


  gotToPage(page: number) {
    this.page = page;
    this.findAllWallets();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllWallets();
  }

  goToPreviousPage() {
    this.page --;
    this.findAllWallets();
  }

  goToLastPage() {
    this.page = this.walletResponse.totalPages as number - 1;
    this.findAllWallets();
  }

  goToNextPage() {
    this.page++;
    this.findAllWallets();
  }

  get isLastPage() {
    return this.page === this.walletResponse.totalPages as number - 1;
  }

}
